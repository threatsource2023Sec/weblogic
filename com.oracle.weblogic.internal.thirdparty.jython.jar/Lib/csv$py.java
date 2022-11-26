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
import org.python.core.PyFloat;
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
@Filename("csv.py")
public class csv$py extends PyFunctionTable implements PyRunnable {
   static csv$py self;
   static final PyCode f$0;
   static final PyCode Dialect$1;
   static final PyCode __init__$2;
   static final PyCode _validate$3;
   static final PyCode excel$4;
   static final PyCode excel_tab$5;
   static final PyCode DictReader$6;
   static final PyCode __init__$7;
   static final PyCode __iter__$8;
   static final PyCode fieldnames$9;
   static final PyCode fieldnames$10;
   static final PyCode next$11;
   static final PyCode DictWriter$12;
   static final PyCode __init__$13;
   static final PyCode writeheader$14;
   static final PyCode _dict_to_list$15;
   static final PyCode writerow$16;
   static final PyCode writerows$17;
   static final PyCode Sniffer$18;
   static final PyCode __init__$19;
   static final PyCode sniff$20;
   static final PyCode dialect$21;
   static final PyCode _guess_quote_and_delimiter$22;
   static final PyCode f$23;
   static final PyCode f$24;
   static final PyCode _guess_delimiter$25;
   static final PyCode f$26;
   static final PyCode f$27;
   static final PyCode has_header$28;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\ncsv.py - read/write/investigate CSV files\n"));
      var1.setline(4);
      PyString.fromInterned("\ncsv.py - read/write/investigate CSV files\n");
      var1.setline(6);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(7);
      String[] var8 = new String[]{"reduce"};
      PyObject[] var9 = imp.importFrom("functools", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("reduce", var4);
      var4 = null;
      var1.setline(8);
      var8 = new String[]{"Error", "__version__", "writer", "reader", "register_dialect", "unregister_dialect", "get_dialect", "list_dialects", "field_size_limit", "QUOTE_MINIMAL", "QUOTE_ALL", "QUOTE_NONNUMERIC", "QUOTE_NONE", "__doc__"};
      var9 = imp.importFrom("_csv", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("Error", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("__version__", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("writer", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("reader", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("register_dialect", var4);
      var4 = null;
      var4 = var9[5];
      var1.setlocal("unregister_dialect", var4);
      var4 = null;
      var4 = var9[6];
      var1.setlocal("get_dialect", var4);
      var4 = null;
      var4 = var9[7];
      var1.setlocal("list_dialects", var4);
      var4 = null;
      var4 = var9[8];
      var1.setlocal("field_size_limit", var4);
      var4 = null;
      var4 = var9[9];
      var1.setlocal("QUOTE_MINIMAL", var4);
      var4 = null;
      var4 = var9[10];
      var1.setlocal("QUOTE_ALL", var4);
      var4 = null;
      var4 = var9[11];
      var1.setlocal("QUOTE_NONNUMERIC", var4);
      var4 = null;
      var4 = var9[12];
      var1.setlocal("QUOTE_NONE", var4);
      var4 = null;
      var4 = var9[13];
      var1.setlocal("__doc__", var4);
      var4 = null;
      var1.setline(13);
      var8 = new String[]{"Dialect"};
      var9 = imp.importFrom("_csv", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_Dialect", var4);
      var4 = null;

      PyException var10;
      try {
         var1.setline(16);
         var8 = new String[]{"StringIO"};
         var9 = imp.importFrom("cStringIO", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("StringIO", var4);
         var4 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(18);
         String[] var12 = new String[]{"StringIO"};
         PyObject[] var13 = imp.importFrom("StringIO", var12, var1, -1);
         PyObject var5 = var13[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(20);
      PyList var11 = new PyList(new PyObject[]{PyString.fromInterned("QUOTE_MINIMAL"), PyString.fromInterned("QUOTE_ALL"), PyString.fromInterned("QUOTE_NONNUMERIC"), PyString.fromInterned("QUOTE_NONE"), PyString.fromInterned("Error"), PyString.fromInterned("Dialect"), PyString.fromInterned("__doc__"), PyString.fromInterned("excel"), PyString.fromInterned("excel_tab"), PyString.fromInterned("field_size_limit"), PyString.fromInterned("reader"), PyString.fromInterned("writer"), PyString.fromInterned("register_dialect"), PyString.fromInterned("get_dialect"), PyString.fromInterned("list_dialects"), PyString.fromInterned("Sniffer"), PyString.fromInterned("unregister_dialect"), PyString.fromInterned("__version__"), PyString.fromInterned("DictReader"), PyString.fromInterned("DictWriter")});
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(26);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("Dialect", var9, Dialect$1);
      var1.setlocal("Dialect", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(57);
      var9 = new PyObject[]{var1.getname("Dialect")};
      var4 = Py.makeClass("excel", var9, excel$4);
      var1.setlocal("excel", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(65);
      var1.getname("register_dialect").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("excel"), (PyObject)var1.getname("excel"));
      var1.setline(67);
      var9 = new PyObject[]{var1.getname("excel")};
      var4 = Py.makeClass("excel_tab", var9, excel_tab$5);
      var1.setlocal("excel_tab", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(70);
      var1.getname("register_dialect").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("excel-tab"), (PyObject)var1.getname("excel_tab"));
      var1.setline(73);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("DictReader", var9, DictReader$6);
      var1.setlocal("DictReader", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(123);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("DictWriter", var9, DictWriter$12);
      var1.setlocal("DictWriter", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);

      try {
         var1.setline(158);
         var1.getname("complex");
      } catch (Throwable var6) {
         var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("NameError"))) {
            throw var10;
         }

         var1.setline(160);
         var4 = var1.getname("float");
         var1.setlocal("complex", var4);
         var4 = null;
      }

      var1.setline(162);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("Sniffer", var9, Sniffer$18);
      var1.setlocal("Sniffer", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Dialect$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Describe an Excel dialect.\n\n    This must be subclassed (see csv.excel).  Valid attributes are:\n    delimiter, quotechar, escapechar, doublequote, skipinitialspace,\n    lineterminator, quoting.\n\n    "));
      var1.setline(33);
      PyString.fromInterned("Describe an Excel dialect.\n\n    This must be subclassed (see csv.excel).  Valid attributes are:\n    delimiter, quotechar, escapechar, doublequote, skipinitialspace,\n    lineterminator, quoting.\n\n    ");
      var1.setline(34);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("_name", var3);
      var3 = null;
      var1.setline(35);
      PyObject var4 = var1.getname("False");
      var1.setlocal("_valid", var4);
      var3 = null;
      var1.setline(37);
      var4 = var1.getname("None");
      var1.setlocal("delimiter", var4);
      var3 = null;
      var1.setline(38);
      var4 = var1.getname("None");
      var1.setlocal("quotechar", var4);
      var3 = null;
      var1.setline(39);
      var4 = var1.getname("None");
      var1.setlocal("escapechar", var4);
      var3 = null;
      var1.setline(40);
      var4 = var1.getname("None");
      var1.setlocal("doublequote", var4);
      var3 = null;
      var1.setline(41);
      var4 = var1.getname("None");
      var1.setlocal("skipinitialspace", var4);
      var3 = null;
      var1.setline(42);
      var4 = var1.getname("None");
      var1.setlocal("lineterminator", var4);
      var3 = null;
      var1.setline(43);
      var4 = var1.getname("None");
      var1.setlocal("quoting", var4);
      var3 = null;
      var1.setline(45);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(50);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _validate$3, (PyObject)null);
      var1.setlocal("_validate", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._ne(var1.getglobal("Dialect"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_valid", var3);
         var3 = null;
      }

      var1.setline(48);
      var1.getlocal(0).__getattr__("_validate").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _validate$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(52);
         var1.getglobal("_Dialect").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("TypeError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(55);
            throw Py.makeException(var1.getglobal("Error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject excel$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Describe the usual properties of Excel-generated CSV files."));
      var1.setline(58);
      PyString.fromInterned("Describe the usual properties of Excel-generated CSV files.");
      var1.setline(59);
      PyString var3 = PyString.fromInterned(",");
      var1.setlocal("delimiter", var3);
      var3 = null;
      var1.setline(60);
      var3 = PyString.fromInterned("\"");
      var1.setlocal("quotechar", var3);
      var3 = null;
      var1.setline(61);
      PyObject var4 = var1.getname("True");
      var1.setlocal("doublequote", var4);
      var3 = null;
      var1.setline(62);
      var4 = var1.getname("False");
      var1.setlocal("skipinitialspace", var4);
      var3 = null;
      var1.setline(63);
      var3 = PyString.fromInterned("\r\n");
      var1.setlocal("lineterminator", var3);
      var3 = null;
      var1.setline(64);
      var4 = var1.getname("QUOTE_MINIMAL");
      var1.setlocal("quoting", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject excel_tab$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Describe the usual properties of Excel-generated TAB-delimited files."));
      var1.setline(68);
      PyString.fromInterned("Describe the usual properties of Excel-generated TAB-delimited files.");
      var1.setline(69);
      PyString var3 = PyString.fromInterned("\t");
      var1.setlocal("delimiter", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject DictReader$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(74);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("excel")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$8, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fieldnames$9, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fieldnames", var5);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fieldnames$10, (PyObject)null);
      var5 = var1.getname("fieldnames").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fieldnames", var5);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$11, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_fieldnames", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("restkey", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("restval", var3);
      var3 = null;
      var1.setline(79);
      PyObject var10000 = var1.getglobal("reader");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(5)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(6), var1.getlocal(7));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("reader", var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("dialect", var3);
      var3 = null;
      var1.setline(81);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"line_num", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$8(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fieldnames$9(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getlocal(0).__getattr__("_fieldnames");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(90);
            var3 = var1.getlocal(0).__getattr__("reader").__getattr__("next").__call__(var2);
            var1.getlocal(0).__setattr__("_fieldnames", var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("StopIteration"))) {
               throw var5;
            }

            var1.setline(92);
         }
      }

      var1.setline(93);
      var3 = var1.getlocal(0).__getattr__("reader").__getattr__("line_num");
      var1.getlocal(0).__setattr__("line_num", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(0).__getattr__("_fieldnames");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fieldnames$10(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_fieldnames", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject next$11(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(0).__getattr__("line_num");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var1.getlocal(0).__getattr__("fieldnames");
      }

      var1.setline(104);
      var3 = var1.getlocal(0).__getattr__("reader").__getattr__("next").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(105);
      var3 = var1.getlocal(0).__getattr__("reader").__getattr__("line_num");
      var1.getlocal(0).__setattr__("line_num", var3);
      var3 = null;

      while(true) {
         var1.setline(110);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(new PyList(Py.EmptyObjects));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(112);
            var3 = var1.getglobal("dict").__call__(var2, var1.getglobal("zip").__call__(var2, var1.getlocal(0).__getattr__("fieldnames"), var1.getlocal(1)));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(113);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("fieldnames"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(114);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(115);
            var3 = var1.getlocal(3);
            var10000 = var3._lt(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(116);
               var3 = var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
               var1.getlocal(2).__setitem__(var1.getlocal(0).__getattr__("restkey"), var3);
               var3 = null;
            } else {
               var1.setline(117);
               var3 = var1.getlocal(3);
               var10000 = var3._gt(var1.getlocal(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(118);
                  var3 = var1.getlocal(0).__getattr__("fieldnames").__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null).__iter__();

                  while(true) {
                     var1.setline(118);
                     PyObject var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(119);
                     PyObject var5 = var1.getlocal(0).__getattr__("restval");
                     var1.getlocal(2).__setitem__(var1.getlocal(5), var5);
                     var5 = null;
                  }
               }
            }

            var1.setline(120);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(111);
         var3 = var1.getlocal(0).__getattr__("reader").__getattr__("next").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject DictWriter$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(124);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("raise"), PyString.fromInterned("excel")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeheader$14, (PyObject)null);
      var1.setlocal("writeheader", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dict_to_list$15, (PyObject)null);
      var1.setlocal("_dict_to_list", var4);
      var3 = null;
      var1.setline(147);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writerow$16, (PyObject)null);
      var1.setlocal("writerow", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writerows$17, (PyObject)null);
      var1.setlocal("writerows", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("fieldnames", var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("restval", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(4).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("raise"), PyString.fromInterned("ignore")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(129);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("extrasaction (%s) must be 'raise' or 'ignore'")._mod(var1.getlocal(4)));
      } else {
         var1.setline(132);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("extrasaction", var3);
         var3 = null;
         var1.setline(133);
         var10000 = var1.getglobal("writer");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(5)};
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(6), var1.getlocal(7));
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("writer", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject writeheader$14(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var3 = var1.getglobal("dict").__call__(var2, var1.getglobal("zip").__call__(var2, var1.getlocal(0).__getattr__("fieldnames"), var1.getlocal(0).__getattr__("fieldnames")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(137);
      var1.getlocal(0).__getattr__("writerow").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dict_to_list$15(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = var1.getlocal(0).__getattr__("extrasaction");
      PyObject var10000 = var3._eq(PyString.fromInterned("raise"));
      var3 = null;
      PyObject var4;
      PyList var6;
      PyList var7;
      if (var10000.__nonzero__()) {
         var1.setline(141);
         var7 = new PyList();
         var3 = var7.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(141);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(141);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(141);
               var1.dellocal(3);
               var6 = var7;
               var1.setlocal(2, var6);
               var3 = null;
               var1.setline(142);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(143);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("dict contains fields not in fieldnames: ")._add(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(2)))));
               }
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(141);
            PyObject var5 = var1.getlocal(4);
            PyObject var10001 = var5._notin(var1.getlocal(0).__getattr__("fieldnames"));
            var5 = null;
            if (var10001.__nonzero__()) {
               var1.setline(141);
               var1.getlocal(3).__call__(var2, var1.getlocal(4));
            }
         }
      }

      var1.setline(145);
      var7 = new PyList();
      var3 = var7.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getlocal(0).__getattr__("fieldnames").__iter__();

      while(true) {
         var1.setline(145);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(145);
            var1.dellocal(5);
            var6 = var7;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(6, var4);
         var1.setline(145);
         var1.getlocal(5).__call__(var2, var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("restval")));
      }
   }

   public PyObject writerow$16(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("writerow").__call__(var2, var1.getlocal(0).__getattr__("_dict_to_list").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writerows$17(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(152);
      PyObject var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(152);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(154);
            var5 = var1.getlocal(0).__getattr__("writer").__getattr__("writerows").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(153);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_dict_to_list").__call__(var2, var1.getlocal(3)));
      }
   }

   public PyObject Sniffer$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    \"Sniffs\" the format of a CSV file (i.e. delimiter, quotechar)\n    Returns a Dialect object.\n    "));
      var1.setline(166);
      PyString.fromInterned("\n    \"Sniffs\" the format of a CSV file (i.e. delimiter, quotechar)\n    Returns a Dialect object.\n    ");
      var1.setline(167);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(172);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, sniff$20, PyString.fromInterned("\n        Returns a dialect (or None) corresponding to the sample\n        "));
      var1.setlocal("sniff", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _guess_quote_and_delimiter$22, PyString.fromInterned("\n        Looks for text enclosed between two identical quotes\n        (the probable quotechar) which are preceded and followed\n        by the same character (the probable delimiter).\n        For example:\n                         ,'some text',\n        The quote with the most wins, same with the delimiter.\n        If there is no quotechar the delimiter can't be determined\n        this way.\n        "));
      var1.setlocal("_guess_quote_and_delimiter", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _guess_delimiter$25, PyString.fromInterned("\n        The delimiter /should/ occur the same number of times on\n        each row. However, due to malformed data, it may not. We don't want\n        an all or nothing approach, so we allow for small variations in this\n        number.\n          1) build a table of the frequency of each character on every line.\n          2) build a table of frequencies of this frequency (meta-frequency?),\n             e.g.  'x occurred 5 times in 10 rows, 6 times in 1000 rows,\n             7 times in 2 rows'\n          3) use the mode of the meta-frequency to determine the /expected/\n             frequency for that character\n          4) find out how often the character actually meets that goal\n          5) the character that best meets its goal is the delimiter\n        For performance reasons, the data is evaluated in chunks, so it can\n        try and evaluate the smallest portion of the data possible, evaluating\n        additional chunks as necessary.\n        "));
      var1.setlocal("_guess_delimiter", var4);
      var3 = null;
      var1.setline(382);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_header$28, (PyObject)null);
      var1.setlocal("has_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned(","), PyString.fromInterned("\t"), PyString.fromInterned(";"), PyString.fromInterned(" "), PyString.fromInterned(":")});
      var1.getlocal(0).__setattr__((String)"preferred", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sniff$20(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyString.fromInterned("\n        Returns a dialect (or None) corresponding to the sample\n        ");
      var1.setline(177);
      PyObject var3 = var1.getlocal(0).__getattr__("_guess_quote_and_delimiter").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(179);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(180);
         var3 = var1.getlocal(0).__getattr__("_guess_delimiter").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(183);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(184);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Could not determine delimiter"));
      } else {
         var1.setline(186);
         PyObject[] var6 = new PyObject[]{var1.getglobal("Dialect")};
         PyObject var7 = Py.makeClass("dialect", var6, dialect$21);
         var1.setlocal(7, var7);
         var4 = null;
         Arrays.fill(var6, (Object)null);
         var1.setline(192);
         var3 = var1.getlocal(4);
         var1.getlocal(7).__setattr__("doublequote", var3);
         var3 = null;
         var1.setline(193);
         var3 = var1.getlocal(5);
         var1.getlocal(7).__setattr__("delimiter", var3);
         var3 = null;
         var1.setline(195);
         Object var10000 = var1.getlocal(3);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("\"");
         }

         Object var8 = var10000;
         var1.getlocal(7).__setattr__((String)"quotechar", (PyObject)var8);
         var3 = null;
         var1.setline(196);
         var3 = var1.getlocal(6);
         var1.getlocal(7).__setattr__("skipinitialspace", var3);
         var3 = null;
         var1.setline(198);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject dialect$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(187);
      PyString var3 = PyString.fromInterned("sniffed");
      var1.setlocal("_name", var3);
      var3 = null;
      var1.setline(188);
      var3 = PyString.fromInterned("\r\n");
      var1.setlocal("lineterminator", var3);
      var3 = null;
      var1.setline(189);
      PyObject var4 = var1.getname("QUOTE_MINIMAL");
      var1.setlocal("quoting", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _guess_quote_and_delimiter$22(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyString.fromInterned("\n        Looks for text enclosed between two identical quotes\n        (the probable quotechar) which are preceded and followed\n        by the same character (the probable delimiter).\n        For example:\n                         ,'some text',\n        The quote with the most wins, same with the delimiter.\n        If there is no quotechar the delimiter can't be determined\n        this way.\n        ");
      var1.setline(213);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(214);
      PyObject var9 = (new PyTuple(new PyObject[]{PyString.fromInterned("(?P<delim>[^\\w\n\"'])(?P<space> ?)(?P<quote>[\"']).*?(?P=quote)(?P=delim)"), PyString.fromInterned("(?:^|\n)(?P<quote>[\"']).*?(?P=quote)(?P<delim>[^\\w\n\"'])(?P<space> ?)"), PyString.fromInterned("(?P<delim>>[^\\w\n\"'])(?P<space> ?)(?P<quote>[\"']).*?(?P=quote)(?:$|\n)"), PyString.fromInterned("(?:^|\n)(?P<quote>[\"']).*?(?P=quote)(?:$|\n)")})).__iter__();

      PyObject var4;
      PyObject var5;
      do {
         var1.setline(214);
         var4 = var9.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(218);
         var5 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(4), var1.getglobal("re").__getattr__("DOTALL")._or(var1.getglobal("re").__getattr__("MULTILINE")));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(219);
         var5 = var1.getlocal(5).__getattr__("findall").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(220);
      } while(!var1.getlocal(3).__nonzero__());

      var1.setline(223);
      PyTuple var10;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(225);
         var10 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("False"), var1.getglobal("None"), Py.newInteger(0)});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(226);
         PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(6, var11);
         var4 = null;
         var1.setline(227);
         var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(7, var11);
         var4 = null;
         var1.setline(228);
         PyInteger var12 = Py.newInteger(0);
         var1.setlocal(8, var12);
         var4 = null;
         var1.setline(229);
         var4 = var1.getlocal(3).__iter__();

         while(true) {
            PyObject var6;
            while(true) {
               PyException var14;
               PyObject var10000;
               while(true) {
                  var1.setline(229);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(248);
                     var10000 = var1.getglobal("reduce");
                     var1.setline(248);
                     PyObject[] var13 = new PyObject[]{var1.getlocal(6)};
                     var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var13, f$23)), (PyObject)var1.getlocal(6).__getattr__("keys").__call__(var2));
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(251);
                     PyString var15;
                     if (var1.getlocal(7).__nonzero__()) {
                        var1.setline(252);
                        var10000 = var1.getglobal("reduce");
                        var1.setline(252);
                        var13 = new PyObject[]{var1.getlocal(7)};
                        var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var13, f$24)), (PyObject)var1.getlocal(7).__getattr__("keys").__call__(var2));
                        var1.setlocal(13, var4);
                        var4 = null;
                        var1.setline(254);
                        var4 = var1.getlocal(7).__getitem__(var1.getlocal(13));
                        var10000 = var4._eq(var1.getlocal(8));
                        var4 = null;
                        var4 = var10000;
                        var1.setlocal(14, var4);
                        var4 = null;
                        var1.setline(255);
                        var4 = var1.getlocal(13);
                        var10000 = var4._eq(PyString.fromInterned("\n"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(256);
                           var15 = PyString.fromInterned("");
                           var1.setlocal(13, var15);
                           var4 = null;
                        }
                     } else {
                        var1.setline(259);
                        var15 = PyString.fromInterned("");
                        var1.setlocal(13, var15);
                        var4 = null;
                        var1.setline(260);
                        var12 = Py.newInteger(0);
                        var1.setlocal(14, var12);
                        var4 = null;
                     }

                     var1.setline(264);
                     var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("((%(delim)s)|^)\\W*%(quote)s[^%(delim)s\\n]*%(quote)s[^%(delim)s\\n]*%(quote)s\\W*((%(delim)s)|$)")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("delim"), var1.getlocal(13), PyString.fromInterned("quote"), var1.getlocal(12)})), var1.getglobal("re").__getattr__("MULTILINE"));
                     var1.setlocal(15, var4);
                     var4 = null;
                     var1.setline(269);
                     if (var1.getlocal(15).__getattr__("search").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                        var1.setline(270);
                        var4 = var1.getglobal("True");
                        var1.setlocal(16, var4);
                        var4 = null;
                     } else {
                        var1.setline(272);
                        var4 = var1.getglobal("False");
                        var1.setlocal(16, var4);
                        var4 = null;
                     }

                     var1.setline(274);
                     var10 = new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(16), var1.getlocal(13), var1.getlocal(14)});
                     var1.f_lasti = -1;
                     return var10;
                  }

                  var1.setlocal(9, var5);
                  var1.setline(230);
                  var6 = var1.getlocal(5).__getattr__("groupindex").__getitem__(PyString.fromInterned("quote"))._sub(Py.newInteger(1));
                  var1.setlocal(10, var6);
                  var6 = null;
                  var1.setline(231);
                  var6 = var1.getlocal(9).__getitem__(var1.getlocal(10));
                  var1.setlocal(11, var6);
                  var6 = null;
                  var1.setline(232);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(233);
                     var6 = var1.getlocal(6).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
                     var1.getlocal(6).__setitem__(var1.getlocal(11), var6);
                     var6 = null;
                  }

                  try {
                     var1.setline(235);
                     var6 = var1.getlocal(5).__getattr__("groupindex").__getitem__(PyString.fromInterned("delim"))._sub(Py.newInteger(1));
                     var1.setlocal(10, var6);
                     var6 = null;
                     var1.setline(236);
                     var6 = var1.getlocal(9).__getitem__(var1.getlocal(10));
                     var1.setlocal(11, var6);
                     var6 = null;
                     break;
                  } catch (Throwable var7) {
                     var14 = Py.setException(var7, var1);
                     if (!var14.match(var1.getglobal("KeyError"))) {
                        throw var14;
                     }
                  }
               }

               var1.setline(239);
               var10000 = var1.getlocal(11);
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(2);
                  var10000 = var6._is(var1.getglobal("None"));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     var6 = var1.getlocal(11);
                     var10000 = var6._in(var1.getlocal(2));
                     var6 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(240);
                  var6 = var1.getlocal(7).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
                  var1.getlocal(7).__setitem__(var1.getlocal(11), var6);
                  var6 = null;
               }

               try {
                  var1.setline(242);
                  var6 = var1.getlocal(5).__getattr__("groupindex").__getitem__(PyString.fromInterned("space"))._sub(Py.newInteger(1));
                  var1.setlocal(10, var6);
                  var6 = null;
                  break;
               } catch (Throwable var8) {
                  var14 = Py.setException(var8, var1);
                  if (!var14.match(var1.getglobal("KeyError"))) {
                     throw var14;
                  }
               }
            }

            var1.setline(245);
            if (var1.getlocal(9).__getitem__(var1.getlocal(10)).__nonzero__()) {
               var1.setline(246);
               var6 = var1.getlocal(8);
               var6 = var6._iadd(Py.newInteger(1));
               var1.setlocal(8, var6);
            }
         }
      }
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyObject var3 = var1.getlocal(2).__getitem__(var1.getlocal(0));
      PyObject var10000 = var3._gt(var1.getlocal(2).__getitem__(var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0);
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$24(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyObject var3 = var1.getlocal(2).__getitem__(var1.getlocal(0));
      PyObject var10000 = var3._gt(var1.getlocal(2).__getitem__(var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0);
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _guess_delimiter$25(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyString.fromInterned("\n        The delimiter /should/ occur the same number of times on\n        each row. However, due to malformed data, it may not. We don't want\n        an all or nothing approach, so we allow for small variations in this\n        number.\n          1) build a table of the frequency of each character on every line.\n          2) build a table of frequencies of this frequency (meta-frequency?),\n             e.g.  'x occurred 5 times in 10 rows, 6 times in 1000 rows,\n             7 times in 2 rows'\n          3) use the mode of the meta-frequency to determine the /expected/\n             frequency for that character\n          4) find out how often the character actually meets that goal\n          5) the character that best meets its goal is the delimiter\n        For performance reasons, the data is evaluated in chunks, so it can\n        try and evaluate the smallest portion of the data possible, evaluating\n        additional chunks as necessary.\n        ");
      var1.setline(296);
      PyObject var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(298);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(298);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(127)).__iter__();

      while(true) {
         var1.setline(298);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(298);
            var1.dellocal(4);
            PyList var9 = var10000;
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(301);
            var3 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)Py.newInteger(10), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(302);
            PyInteger var10 = Py.newInteger(0);
            var1.setlocal(7, var10);
            var3 = null;
            var1.setline(303);
            PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var11);
            var3 = null;
            var1.setline(304);
            var11 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(9, var11);
            var3 = null;
            var1.setline(305);
            var11 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(10, var11);
            var3 = null;
            var1.setline(306);
            PyTuple var12 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("min").__call__(var2, var1.getlocal(6), var1.getglobal("len").__call__(var2, var1.getlocal(1)))});
            PyObject[] var8 = Py.unpackSequence(var12, 2);
            PyObject var5 = var8[0];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(12, var5);
            var5 = null;
            var3 = null;

            label116:
            while(true) {
               var1.setline(307);
               var4 = var1.getlocal(11);
               PyObject var18 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var4 = null;
               PyObject var6;
               PyObject var7;
               if (!var18.__nonzero__()) {
                  var1.setline(360);
                  if (var1.getlocal(10).__not__().__nonzero__()) {
                     var1.setline(361);
                     var12 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
                     var1.f_lasti = -1;
                     return var12;
                  }

                  var1.setline(364);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
                  var18 = var4._gt(Py.newInteger(1));
                  var4 = null;
                  if (var18.__nonzero__()) {
                     var1.setline(365);
                     var4 = var1.getlocal(0).__getattr__("preferred").__iter__();

                     while(true) {
                        var1.setline(365);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           break;
                        }

                        var1.setlocal(26, var5);
                        var1.setline(366);
                        var6 = var1.getlocal(26);
                        var18 = var6._in(var1.getlocal(10).__getattr__("keys").__call__(var2));
                        var6 = null;
                        if (var18.__nonzero__()) {
                           var1.setline(367);
                           var6 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, var1.getlocal(26));
                           var18 = var6._eq(var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, PyString.fromInterned("%c ")._mod(var1.getlocal(26))));
                           var6 = null;
                           var6 = var18;
                           var1.setlocal(25, var6);
                           var6 = null;
                           var1.setline(369);
                           var12 = new PyTuple(new PyObject[]{var1.getlocal(26), var1.getlocal(25)});
                           var1.f_lasti = -1;
                           return var12;
                        }
                     }
                  }

                  var1.setline(373);
                  var10000 = new PyList();
                  var4 = var10000.__getattr__("append");
                  var1.setlocal(27, var4);
                  var4 = null;
                  var1.setline(373);
                  var4 = var1.getlocal(10).__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(373);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(373);
                        var1.dellocal(27);
                        PyList var14 = var10000;
                        var1.setlocal(17, var14);
                        var4 = null;
                        var1.setline(374);
                        var1.getlocal(17).__getattr__("sort").__call__(var2);
                        var1.setline(375);
                        var4 = var1.getlocal(17).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                        var1.setlocal(24, var4);
                        var4 = null;
                        var1.setline(377);
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, var1.getlocal(24));
                        var18 = var4._eq(var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, PyString.fromInterned("%c ")._mod(var1.getlocal(24))));
                        var4 = null;
                        var4 = var18;
                        var1.setlocal(25, var4);
                        var4 = null;
                        var1.setline(379);
                        var12 = new PyTuple(new PyObject[]{var1.getlocal(24), var1.getlocal(25)});
                        var1.f_lasti = -1;
                        return var12;
                     }

                     PyObject[] var15 = Py.unpackSequence(var5, 2);
                     var7 = var15[0];
                     var1.setlocal(22, var7);
                     var7 = null;
                     var7 = var15[1];
                     var1.setlocal(23, var7);
                     var7 = null;
                     var1.setline(373);
                     var1.getlocal(27).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(23), var1.getlocal(22)})));
                  }
               }

               var1.setline(308);
               var3 = var1.getlocal(7);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(7, var3);
               var1.setline(309);
               var3 = var1.getlocal(1).__getslice__(var1.getlocal(11), var1.getlocal(12), (PyObject)null).__iter__();

               while(true) {
                  var1.setline(309);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(318);
                     var3 = var1.getlocal(8).__getattr__("keys").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(318);
                        var4 = var3.__iternext__();
                        PyObject[] var13;
                        if (var4 == null) {
                           var1.setline(336);
                           var3 = var1.getlocal(9).__getattr__("items").__call__(var2);
                           var1.setlocal(18, var3);
                           var3 = null;
                           var1.setline(337);
                           var3 = var1.getglobal("float").__call__(var2, var1.getlocal(6)._mul(var1.getlocal(7)));
                           var1.setlocal(19, var3);
                           var3 = null;
                           var1.setline(339);
                           PyFloat var17 = Py.newFloat(1.0);
                           var1.setlocal(20, var17);
                           var3 = null;
                           var1.setline(341);
                           var17 = Py.newFloat(0.9);
                           var1.setlocal(21, var17);
                           var3 = null;

                           while(true) {
                              var1.setline(342);
                              var3 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
                              var18 = var3._eq(Py.newInteger(0));
                              var3 = null;
                              if (var18.__nonzero__()) {
                                 var3 = var1.getlocal(20);
                                 var18 = var3._ge(var1.getlocal(21));
                                 var3 = null;
                              }

                              if (!var18.__nonzero__()) {
                                 var1.setline(350);
                                 var3 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
                                 var18 = var3._eq(Py.newInteger(1));
                                 var3 = null;
                                 if (var18.__nonzero__()) {
                                    var1.setline(351);
                                    var3 = var1.getlocal(10).__getattr__("keys").__call__(var2).__getitem__(Py.newInteger(0));
                                    var1.setlocal(24, var3);
                                    var3 = null;
                                    var1.setline(352);
                                    var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, var1.getlocal(24));
                                    var18 = var3._eq(var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("count").__call__(var2, PyString.fromInterned("%c ")._mod(var1.getlocal(24))));
                                    var3 = null;
                                    var3 = var18;
                                    var1.setlocal(25, var3);
                                    var3 = null;
                                    var1.setline(354);
                                    var12 = new PyTuple(new PyObject[]{var1.getlocal(24), var1.getlocal(25)});
                                    var1.f_lasti = -1;
                                    return var12;
                                 }

                                 var1.setline(357);
                                 var4 = var1.getlocal(12);
                                 var1.setlocal(11, var4);
                                 var4 = null;
                                 var1.setline(358);
                                 var4 = var1.getlocal(12);
                                 var4 = var4._iadd(var1.getlocal(6));
                                 var1.setlocal(12, var4);
                                 continue label116;
                              }

                              var1.setline(343);
                              var3 = var1.getlocal(18).__iter__();

                              while(true) {
                                 var1.setline(343);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(348);
                                    var3 = var1.getlocal(20);
                                    var3 = var3._isub(Py.newFloat(0.01));
                                    var1.setlocal(20, var3);
                                    break;
                                 }

                                 var13 = Py.unpackSequence(var4, 2);
                                 var6 = var13[0];
                                 var1.setlocal(22, var6);
                                 var6 = null;
                                 var6 = var13[1];
                                 var1.setlocal(23, var6);
                                 var6 = null;
                                 var1.setline(344);
                                 var5 = var1.getlocal(23).__getitem__(Py.newInteger(0));
                                 var18 = var5._gt(Py.newInteger(0));
                                 var5 = null;
                                 if (var18.__nonzero__()) {
                                    var5 = var1.getlocal(23).__getitem__(Py.newInteger(1));
                                    var18 = var5._gt(Py.newInteger(0));
                                    var5 = null;
                                 }

                                 if (var18.__nonzero__()) {
                                    var1.setline(345);
                                    var5 = var1.getlocal(23).__getitem__(Py.newInteger(1))._div(var1.getlocal(19));
                                    var18 = var5._ge(var1.getlocal(20));
                                    var5 = null;
                                    if (var18.__nonzero__()) {
                                       var5 = var1.getlocal(2);
                                       var18 = var5._is(var1.getglobal("None"));
                                       var5 = null;
                                       if (!var18.__nonzero__()) {
                                          var5 = var1.getlocal(22);
                                          var18 = var5._in(var1.getlocal(2));
                                          var5 = null;
                                       }
                                    }

                                    if (var18.__nonzero__()) {
                                       var1.setline(347);
                                       var5 = var1.getlocal(23);
                                       var1.getlocal(10).__setitem__(var1.getlocal(22), var5);
                                       var5 = null;
                                    }
                                 }
                              }
                           }
                        }

                        var1.setlocal(14, var4);
                        var1.setline(319);
                        var5 = var1.getlocal(8).__getitem__(var1.getlocal(14)).__getattr__("items").__call__(var2);
                        var1.setlocal(17, var5);
                        var5 = null;
                        var1.setline(320);
                        var5 = var1.getglobal("len").__call__(var2, var1.getlocal(17));
                        var18 = var5._eq(Py.newInteger(1));
                        var5 = null;
                        if (var18.__nonzero__()) {
                           var5 = var1.getlocal(17).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                           var18 = var5._eq(Py.newInteger(0));
                           var5 = null;
                        }

                        if (!var18.__nonzero__()) {
                           var1.setline(323);
                           var5 = var1.getglobal("len").__call__(var2, var1.getlocal(17));
                           var18 = var5._gt(Py.newInteger(1));
                           var5 = null;
                           if (var18.__nonzero__()) {
                              var1.setline(324);
                              var18 = var1.getglobal("reduce");
                              var1.setline(324);
                              var13 = Py.EmptyObjects;
                              var5 = var18.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var13, f$26)), (PyObject)var1.getlocal(17));
                              var1.getlocal(9).__setitem__(var1.getlocal(14), var5);
                              var5 = null;
                              var1.setline(328);
                              var1.getlocal(17).__getattr__("remove").__call__(var2, var1.getlocal(9).__getitem__(var1.getlocal(14)));
                              var1.setline(329);
                              PyObject[] var10002 = new PyObject[]{var1.getlocal(9).__getitem__(var1.getlocal(14)).__getitem__(Py.newInteger(0)), null};
                              PyObject var10005 = var1.getlocal(9).__getitem__(var1.getlocal(14)).__getitem__(Py.newInteger(1));
                              PyObject var10006 = var1.getglobal("reduce");
                              var1.setline(330);
                              var13 = Py.EmptyObjects;
                              var10002[1] = var10005._sub(var10006.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var13, f$27)), (PyObject)var1.getlocal(17)).__getitem__(Py.newInteger(1)));
                              PyTuple var16 = new PyTuple(var10002);
                              var1.getlocal(9).__setitem__((PyObject)var1.getlocal(14), var16);
                              var5 = null;
                           } else {
                              var1.setline(333);
                              var5 = var1.getlocal(17).__getitem__(Py.newInteger(0));
                              var1.getlocal(9).__setitem__(var1.getlocal(14), var5);
                              var5 = null;
                           }
                        }
                     }
                  }

                  var1.setlocal(13, var4);
                  var1.setline(310);
                  var5 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(310);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(14, var6);
                     var1.setline(311);
                     var7 = var1.getlocal(8).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(14), (PyObject)(new PyDictionary(Py.EmptyObjects)));
                     var1.setlocal(15, var7);
                     var7 = null;
                     var1.setline(313);
                     var7 = var1.getlocal(13).__getattr__("count").__call__(var2, var1.getlocal(14));
                     var1.setlocal(16, var7);
                     var7 = null;
                     var1.setline(315);
                     var7 = var1.getlocal(15).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(16), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
                     var1.getlocal(15).__setitem__(var1.getlocal(16), var7);
                     var7 = null;
                     var1.setline(316);
                     var7 = var1.getlocal(15);
                     var1.getlocal(8).__setitem__(var1.getlocal(14), var7);
                     var7 = null;
                  }
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(298);
         var1.getlocal(4).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(5)));
      }
   }

   public PyObject f$26(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._gt(var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0);
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(0).__getitem__(Py.newInteger(1))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_header$28(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getglobal("reader").__call__(var2, var1.getglobal("StringIO").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("sniff").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(394);
      var3 = var1.getlocal(2).__getattr__("next").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(396);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(397);
      PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var12);
      var3 = null;
      var1.setline(398);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(4)).__iter__();

      while(true) {
         var1.setline(398);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(400);
            PyInteger var13 = Py.newInteger(0);
            var1.setlocal(7, var13);
            var3 = null;
            var1.setline(401);
            var3 = var1.getlocal(2).__iter__();

            PyObject var6;
            PyObject var10000;
            while(true) {
               var1.setline(401);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(8, var4);
               var1.setline(403);
               var5 = var1.getlocal(7);
               var10000 = var5._gt(Py.newInteger(20));
               var5 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(405);
               var5 = var1.getlocal(7);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(7, var5);
               var1.setline(407);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
               var10000 = var5._ne(var1.getlocal(4));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(410);
                  var5 = var1.getlocal(5).__getattr__("keys").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(410);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(9, var6);
                     var1.setline(412);
                     PyObject var7 = (new PyList(new PyObject[]{var1.getglobal("int"), var1.getglobal("long"), var1.getglobal("float"), var1.getglobal("complex")})).__iter__();

                     while(true) {
                        var1.setline(412);
                        PyObject var8 = var7.__iternext__();
                        PyException var9;
                        if (var8 == null) {
                           var1.setline(420);
                           PyObject var16 = var1.getglobal("len").__call__(var2, var1.getlocal(8).__getitem__(var1.getlocal(9)));
                           var1.setlocal(10, var16);
                           var9 = null;
                           break;
                        }

                        var1.setlocal(10, var8);

                        try {
                           var1.setline(414);
                           var1.getlocal(10).__call__(var2, var1.getlocal(8).__getitem__(var1.getlocal(9)));
                           break;
                        } catch (Throwable var11) {
                           var9 = Py.setException(var11, var1);
                           if (!var9.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("OverflowError")}))) {
                              throw var9;
                           }

                           var1.setline(417);
                        }
                     }

                     var1.setline(423);
                     var7 = var1.getlocal(10);
                     var10000 = var7._eq(var1.getglobal("long"));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(424);
                        var7 = var1.getglobal("int");
                        var1.setlocal(10, var7);
                        var7 = null;
                     }

                     var1.setline(426);
                     var7 = var1.getlocal(10);
                     var10000 = var7._ne(var1.getlocal(5).__getitem__(var1.getlocal(9)));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(427);
                        var7 = var1.getlocal(5).__getitem__(var1.getlocal(9));
                        var10000 = var7._is(var1.getglobal("None"));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(428);
                           var7 = var1.getlocal(10);
                           var1.getlocal(5).__setitem__(var1.getlocal(9), var7);
                           var7 = null;
                        } else {
                           var1.setline(432);
                           var1.getlocal(5).__delitem__(var1.getlocal(9));
                        }
                     }
                  }
               }
            }

            var1.setline(436);
            var13 = Py.newInteger(0);
            var1.setlocal(11, var13);
            var3 = null;
            var1.setline(437);
            var3 = var1.getlocal(5).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               while(true) {
                  var1.setline(437);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(451);
                     var3 = var1.getlocal(11);
                     var10000 = var3._gt(Py.newInteger(0));
                     var3 = null;
                     var3 = var10000;
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var14 = Py.unpackSequence(var4, 2);
                  var6 = var14[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var14[1];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var1.setline(438);
                  var5 = var1.getglobal("type").__call__(var2, var1.getlocal(12));
                  var10000 = var5._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(439);
                     var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(9)));
                     var10000 = var5._ne(var1.getlocal(12));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(440);
                        var5 = var1.getlocal(11);
                        var5 = var5._iadd(Py.newInteger(1));
                        var1.setlocal(11, var5);
                     } else {
                        var1.setline(442);
                        var5 = var1.getlocal(11);
                        var5 = var5._isub(Py.newInteger(1));
                        var1.setlocal(11, var5);
                     }
                  } else {
                     try {
                        var1.setline(445);
                        var1.getlocal(12).__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(9)));
                     } catch (Throwable var10) {
                        PyException var15 = Py.setException(var10, var1);
                        if (var15.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("TypeError")}))) {
                           var1.setline(447);
                           var6 = var1.getlocal(11);
                           var6 = var6._iadd(Py.newInteger(1));
                           var1.setlocal(11, var6);
                           continue;
                        }

                        throw var15;
                     }

                     var1.setline(449);
                     var6 = var1.getlocal(11);
                     var6 = var6._isub(Py.newInteger(1));
                     var1.setlocal(11, var6);
                  }
               }
            }
         }

         var1.setlocal(6, var4);
         var1.setline(398);
         var5 = var1.getglobal("None");
         var1.getlocal(5).__setitem__(var1.getlocal(6), var5);
         var5 = null;
      }
   }

   public csv$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Dialect$1 = Py.newCode(0, var2, var1, "Dialect", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 45, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "e"};
      _validate$3 = Py.newCode(1, var2, var1, "_validate", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      excel$4 = Py.newCode(0, var2, var1, "excel", 57, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      excel_tab$5 = Py.newCode(0, var2, var1, "excel_tab", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DictReader$6 = Py.newCode(0, var2, var1, "DictReader", 73, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "fieldnames", "restkey", "restval", "dialect", "args", "kwds"};
      __init__$7 = Py.newCode(8, var2, var1, "__init__", 74, true, true, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$8 = Py.newCode(1, var2, var1, "__iter__", 83, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fieldnames$9 = Py.newCode(1, var2, var1, "fieldnames", 86, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      fieldnames$10 = Py.newCode(2, var2, var1, "fieldnames", 96, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "row", "d", "lf", "lr", "key"};
      next$11 = Py.newCode(1, var2, var1, "next", 100, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DictWriter$12 = Py.newCode(0, var2, var1, "DictWriter", 123, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "fieldnames", "restval", "extrasaction", "dialect", "args", "kwds"};
      __init__$13 = Py.newCode(8, var2, var1, "__init__", 124, true, true, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header"};
      writeheader$14 = Py.newCode(1, var2, var1, "writeheader", 135, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rowdict", "wrong_fields", "_[141_28]", "k", "_[145_16]", "key"};
      _dict_to_list$15 = Py.newCode(2, var2, var1, "_dict_to_list", 139, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rowdict"};
      writerow$16 = Py.newCode(2, var2, var1, "writerow", 147, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rowdicts", "rows", "rowdict"};
      writerows$17 = Py.newCode(2, var2, var1, "writerows", 150, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Sniffer$18 = Py.newCode(0, var2, var1, "Sniffer", 162, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$19 = Py.newCode(1, var2, var1, "__init__", 167, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sample", "delimiters", "quotechar", "doublequote", "delimiter", "skipinitialspace", "dialect"};
      sniff$20 = Py.newCode(3, var2, var1, "sniff", 172, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dialect$21 = Py.newCode(0, var2, var1, "dialect", 186, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data", "delimiters", "matches", "restr", "regexp", "quotes", "delims", "spaces", "m", "n", "key", "quotechar", "delim", "skipinitialspace", "dq_regexp", "doublequote"};
      _guess_quote_and_delimiter$22 = Py.newCode(3, var2, var1, "_guess_quote_and_delimiter", 201, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "quotes"};
      f$23 = Py.newCode(3, var2, var1, "<lambda>", 248, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "delims"};
      f$24 = Py.newCode(3, var2, var1, "<lambda>", 252, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "delimiters", "ascii", "_[298_17]", "c", "chunkLength", "iteration", "charFrequency", "modes", "delims", "start", "end", "line", "char", "metaFrequency", "freq", "items", "modeList", "total", "consistency", "threshold", "k", "v", "delim", "skipinitialspace", "d", "_[373_17]"};
      _guess_delimiter$25 = Py.newCode(3, var2, var1, "_guess_delimiter", 277, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      f$26 = Py.newCode(2, var2, var1, "<lambda>", 324, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      f$27 = Py.newCode(2, var2, var1, "<lambda>", 330, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sample", "rdr", "header", "columns", "columnTypes", "i", "checked", "row", "col", "thisType", "hasHeader", "colType"};
      has_header$28 = Py.newCode(2, var2, var1, "has_header", 382, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new csv$py("csv$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(csv$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Dialect$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._validate$3(var2, var3);
         case 4:
            return this.excel$4(var2, var3);
         case 5:
            return this.excel_tab$5(var2, var3);
         case 6:
            return this.DictReader$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.__iter__$8(var2, var3);
         case 9:
            return this.fieldnames$9(var2, var3);
         case 10:
            return this.fieldnames$10(var2, var3);
         case 11:
            return this.next$11(var2, var3);
         case 12:
            return this.DictWriter$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.writeheader$14(var2, var3);
         case 15:
            return this._dict_to_list$15(var2, var3);
         case 16:
            return this.writerow$16(var2, var3);
         case 17:
            return this.writerows$17(var2, var3);
         case 18:
            return this.Sniffer$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.sniff$20(var2, var3);
         case 21:
            return this.dialect$21(var2, var3);
         case 22:
            return this._guess_quote_and_delimiter$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         case 24:
            return this.f$24(var2, var3);
         case 25:
            return this._guess_delimiter$25(var2, var3);
         case 26:
            return this.f$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.has_header$28(var2, var3);
         default:
            return null;
      }
   }
}
