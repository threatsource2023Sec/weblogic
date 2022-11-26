package unittest.test;

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
@Filename("unittest/test/test_loader.py")
public class test_loader$py extends PyFunctionTable implements PyRunnable {
   static test_loader$py self;
   static final PyCode f$0;
   static final PyCode Test_TestLoader$1;
   static final PyCode test_loadTestsFromTestCase$2;
   static final PyCode Foo$3;
   static final PyCode test_1$4;
   static final PyCode test_2$5;
   static final PyCode foo_bar$6;
   static final PyCode test_loadTestsFromTestCase__no_matches$7;
   static final PyCode Foo$8;
   static final PyCode foo_bar$9;
   static final PyCode test_loadTestsFromTestCase__TestSuite_subclass$10;
   static final PyCode NotATestCase$11;
   static final PyCode test_loadTestsFromTestCase__default_method_name$12;
   static final PyCode Foo$13;
   static final PyCode runTest$14;
   static final PyCode test_loadTestsFromModule__TestCase_subclass$15;
   static final PyCode MyTestCase$16;
   static final PyCode test$17;
   static final PyCode test_loadTestsFromModule__no_TestCase_instances$18;
   static final PyCode test_loadTestsFromModule__no_TestCase_tests$19;
   static final PyCode MyTestCase$20;
   static final PyCode test_loadTestsFromModule__not_a_module$21;
   static final PyCode MyTestCase$22;
   static final PyCode test$23;
   static final PyCode NotAModule$24;
   static final PyCode test_loadTestsFromModule__load_tests$25;
   static final PyCode MyTestCase$26;
   static final PyCode test$27;
   static final PyCode load_tests$28;
   static final PyCode test_loadTestsFromModule__faulty_load_tests$29;
   static final PyCode load_tests$30;
   static final PyCode test_loadTestsFromName__empty_name$31;
   static final PyCode test_loadTestsFromName__malformed_name$32;
   static final PyCode test_loadTestsFromName__unknown_module_name$33;
   static final PyCode test_loadTestsFromName__unknown_attr_name$34;
   static final PyCode test_loadTestsFromName__relative_unknown_name$35;
   static final PyCode test_loadTestsFromName__relative_empty_name$36;
   static final PyCode test_loadTestsFromName__relative_malformed_name$37;
   static final PyCode test_loadTestsFromName__relative_not_a_module$38;
   static final PyCode MyTestCase$39;
   static final PyCode test$40;
   static final PyCode NotAModule$41;
   static final PyCode test_loadTestsFromName__relative_bad_object$42;
   static final PyCode test_loadTestsFromName__relative_TestCase_subclass$43;
   static final PyCode MyTestCase$44;
   static final PyCode test$45;
   static final PyCode test_loadTestsFromName__relative_TestSuite$46;
   static final PyCode MyTestCase$47;
   static final PyCode test$48;
   static final PyCode test_loadTestsFromName__relative_testmethod$49;
   static final PyCode MyTestCase$50;
   static final PyCode test$51;
   static final PyCode test_loadTestsFromName__relative_invalid_testmethod$52;
   static final PyCode MyTestCase$53;
   static final PyCode test$54;
   static final PyCode test_loadTestsFromName__callable__TestSuite$55;
   static final PyCode f$56;
   static final PyCode f$57;
   static final PyCode return_TestSuite$58;
   static final PyCode test_loadTestsFromName__callable__TestCase_instance$59;
   static final PyCode f$60;
   static final PyCode return_TestCase$61;
   static final PyCode test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass$62;
   static final PyCode SubTestSuite$63;
   static final PyCode f$64;
   static final PyCode return_TestCase$65;
   static final PyCode test_loadTestsFromName__relative_testmethod_ProperSuiteClass$66;
   static final PyCode SubTestSuite$67;
   static final PyCode MyTestCase$68;
   static final PyCode test$69;
   static final PyCode test_loadTestsFromName__callable__wrong_type$70;
   static final PyCode return_wrong$71;
   static final PyCode test_loadTestsFromName__module_not_loaded$72;
   static final PyCode test_loadTestsFromNames__empty_name_list$73;
   static final PyCode test_loadTestsFromNames__relative_empty_name_list$74;
   static final PyCode test_loadTestsFromNames__empty_name$75;
   static final PyCode test_loadTestsFromNames__malformed_name$76;
   static final PyCode test_loadTestsFromNames__unknown_module_name$77;
   static final PyCode test_loadTestsFromNames__unknown_attr_name$78;
   static final PyCode test_loadTestsFromNames__unknown_name_relative_1$79;
   static final PyCode test_loadTestsFromNames__unknown_name_relative_2$80;
   static final PyCode test_loadTestsFromNames__relative_empty_name$81;
   static final PyCode test_loadTestsFromNames__relative_malformed_name$82;
   static final PyCode test_loadTestsFromNames__relative_not_a_module$83;
   static final PyCode MyTestCase$84;
   static final PyCode test$85;
   static final PyCode NotAModule$86;
   static final PyCode test_loadTestsFromNames__relative_bad_object$87;
   static final PyCode test_loadTestsFromNames__relative_TestCase_subclass$88;
   static final PyCode MyTestCase$89;
   static final PyCode test$90;
   static final PyCode test_loadTestsFromNames__relative_TestSuite$91;
   static final PyCode MyTestCase$92;
   static final PyCode test$93;
   static final PyCode test_loadTestsFromNames__relative_testmethod$94;
   static final PyCode MyTestCase$95;
   static final PyCode test$96;
   static final PyCode test_loadTestsFromNames__relative_invalid_testmethod$97;
   static final PyCode MyTestCase$98;
   static final PyCode test$99;
   static final PyCode test_loadTestsFromNames__callable__TestSuite$100;
   static final PyCode f$101;
   static final PyCode f$102;
   static final PyCode return_TestSuite$103;
   static final PyCode test_loadTestsFromNames__callable__TestCase_instance$104;
   static final PyCode f$105;
   static final PyCode return_TestCase$106;
   static final PyCode test_loadTestsFromNames__callable__call_staticmethod$107;
   static final PyCode Test1$108;
   static final PyCode test$109;
   static final PyCode Foo$110;
   static final PyCode foo$111;
   static final PyCode test_loadTestsFromNames__callable__wrong_type$112;
   static final PyCode return_wrong$113;
   static final PyCode test_loadTestsFromNames__module_not_loaded$114;
   static final PyCode test_getTestCaseNames$115;
   static final PyCode Test$116;
   static final PyCode test_1$117;
   static final PyCode test_2$118;
   static final PyCode foobar$119;
   static final PyCode test_getTestCaseNames__no_tests$120;
   static final PyCode Test$121;
   static final PyCode foobar$122;
   static final PyCode test_getTestCaseNames__not_a_TestCase$123;
   static final PyCode BadCase$124;
   static final PyCode test_foo$125;
   static final PyCode test_getTestCaseNames__inheritance$126;
   static final PyCode TestP$127;
   static final PyCode test_1$128;
   static final PyCode test_2$129;
   static final PyCode foobar$130;
   static final PyCode TestC$131;
   static final PyCode test_1$132;
   static final PyCode test_3$133;
   static final PyCode test_testMethodPrefix__loadTestsFromTestCase$134;
   static final PyCode Foo$135;
   static final PyCode test_1$136;
   static final PyCode test_2$137;
   static final PyCode foo_bar$138;
   static final PyCode test_testMethodPrefix__loadTestsFromModule$139;
   static final PyCode Foo$140;
   static final PyCode test_1$141;
   static final PyCode test_2$142;
   static final PyCode foo_bar$143;
   static final PyCode test_testMethodPrefix__loadTestsFromName$144;
   static final PyCode Foo$145;
   static final PyCode test_1$146;
   static final PyCode test_2$147;
   static final PyCode foo_bar$148;
   static final PyCode test_testMethodPrefix__loadTestsFromNames$149;
   static final PyCode Foo$150;
   static final PyCode test_1$151;
   static final PyCode test_2$152;
   static final PyCode foo_bar$153;
   static final PyCode test_testMethodPrefix__default_value$154;
   static final PyCode test_sortTestMethodsUsing__loadTestsFromTestCase$155;
   static final PyCode reversed_cmp$156;
   static final PyCode Foo$157;
   static final PyCode test_1$158;
   static final PyCode test_2$159;
   static final PyCode test_sortTestMethodsUsing__loadTestsFromModule$160;
   static final PyCode reversed_cmp$161;
   static final PyCode Foo$162;
   static final PyCode test_1$163;
   static final PyCode test_2$164;
   static final PyCode test_sortTestMethodsUsing__loadTestsFromName$165;
   static final PyCode reversed_cmp$166;
   static final PyCode Foo$167;
   static final PyCode test_1$168;
   static final PyCode test_2$169;
   static final PyCode test_sortTestMethodsUsing__loadTestsFromNames$170;
   static final PyCode reversed_cmp$171;
   static final PyCode Foo$172;
   static final PyCode test_1$173;
   static final PyCode test_2$174;
   static final PyCode test_sortTestMethodsUsing__getTestCaseNames$175;
   static final PyCode reversed_cmp$176;
   static final PyCode Foo$177;
   static final PyCode test_1$178;
   static final PyCode test_2$179;
   static final PyCode test_sortTestMethodsUsing__default_value$180;
   static final PyCode test_sortTestMethodsUsing__None$181;
   static final PyCode Foo$182;
   static final PyCode test_1$183;
   static final PyCode test_2$184;
   static final PyCode test_suiteClass__loadTestsFromTestCase$185;
   static final PyCode Foo$186;
   static final PyCode test_1$187;
   static final PyCode test_2$188;
   static final PyCode foo_bar$189;
   static final PyCode test_suiteClass__loadTestsFromModule$190;
   static final PyCode Foo$191;
   static final PyCode test_1$192;
   static final PyCode test_2$193;
   static final PyCode foo_bar$194;
   static final PyCode test_suiteClass__loadTestsFromName$195;
   static final PyCode Foo$196;
   static final PyCode test_1$197;
   static final PyCode test_2$198;
   static final PyCode foo_bar$199;
   static final PyCode test_suiteClass__loadTestsFromNames$200;
   static final PyCode Foo$201;
   static final PyCode test_1$202;
   static final PyCode test_2$203;
   static final PyCode foo_bar$204;
   static final PyCode test_suiteClass__default_value$205;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(8);
      PyObject[] var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test_TestLoader", var5, Test_TestLoader$1);
      var1.setlocal("Test_TestLoader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1285);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1286);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_TestLoader$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromTestCase$2, (PyObject)null);
      var1.setlocal("test_loadTestsFromTestCase", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromTestCase__no_matches$7, (PyObject)null);
      var1.setlocal("test_loadTestsFromTestCase__no_matches", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromTestCase__TestSuite_subclass$10, (PyObject)null);
      var1.setlocal("test_loadTestsFromTestCase__TestSuite_subclass", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromTestCase__default_method_name$12, (PyObject)null);
      var1.setlocal("test_loadTestsFromTestCase__default_method_name", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__TestCase_subclass$15, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__TestCase_subclass", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__no_TestCase_instances$18, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__no_TestCase_instances", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__no_TestCase_tests$19, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__no_TestCase_tests", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__not_a_module$21, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__not_a_module", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__load_tests$25, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__load_tests", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromModule__faulty_load_tests$29, (PyObject)null);
      var1.setlocal("test_loadTestsFromModule__faulty_load_tests", var4);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__empty_name$31, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__empty_name", var4);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__malformed_name$32, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__malformed_name", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__unknown_module_name$33, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__unknown_module_name", var4);
      var3 = null;
      var1.setline(252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__unknown_attr_name$34, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__unknown_attr_name", var4);
      var3 = null;
      var1.setline(269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_unknown_name$35, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_unknown_name", var4);
      var3 = null;
      var1.setline(290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_empty_name$36, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_empty_name", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_malformed_name$37, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_malformed_name", var4);
      var3 = null;
      var1.setline(331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_not_a_module$38, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_not_a_module", var4);
      var3 = null;
      var1.setline(352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_bad_object$42, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_bad_object", var4);
      var3 = null;
      var1.setline(366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_TestCase_subclass$43, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_TestCase_subclass", var4);
      var3 = null;
      var1.setline(382);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_TestSuite$46, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_TestSuite", var4);
      var3 = null;
      var1.setline(397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_testmethod$49, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_testmethod", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_invalid_testmethod$52, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_invalid_testmethod", var4);
      var3 = null;
      var1.setline(435);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__callable__TestSuite$55, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__callable__TestSuite", var4);
      var3 = null;
      var1.setline(450);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__callable__TestCase_instance$59, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__callable__TestCase_instance", var4);
      var3 = null;
      var1.setline(467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass$62, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass", var4);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__relative_testmethod_ProperSuiteClass$66, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__relative_testmethod_ProperSuiteClass", var4);
      var3 = null;
      var1.setline(507);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__callable__wrong_type$70, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__callable__wrong_type", var4);
      var3 = null;
      var1.setline(523);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromName__module_not_loaded$72, (PyObject)null);
      var1.setlocal("test_loadTestsFromName__module_not_loaded", var4);
      var3 = null;
      var1.setline(553);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__empty_name_list$73, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__empty_name_list", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_empty_name_list$74, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_empty_name_list", var4);
      var3 = null;
      var1.setline(581);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__empty_name$75, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__empty_name", var4);
      var3 = null;
      var1.setline(597);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__malformed_name$76, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__malformed_name", var4);
      var3 = null;
      var1.setline(616);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__unknown_module_name$77, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__unknown_module_name", var4);
      var3 = null;
      var1.setline(632);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__unknown_attr_name$78, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__unknown_attr_name", var4);
      var3 = null;
      var1.setline(651);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__unknown_name_relative_1$79, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__unknown_name_relative_1", var4);
      var3 = null;
      var1.setline(670);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__unknown_name_relative_2$80, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__unknown_name_relative_2", var4);
      var3 = null;
      var1.setline(691);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_empty_name$81, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_empty_name", var4);
      var3 = null;
      var1.setline(709);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_malformed_name$82, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_malformed_name", var4);
      var3 = null;
      var1.setline(729);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_not_a_module$83, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_not_a_module", var4);
      var3 = null;
      var1.setline(750);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_bad_object$87, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_bad_object", var4);
      var3 = null;
      var1.setline(764);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_TestCase_subclass$88, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_TestCase_subclass", var4);
      var3 = null;
      var1.setline(780);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_TestSuite$91, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_TestSuite", var4);
      var3 = null;
      var1.setline(795);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_testmethod$94, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_testmethod", var4);
      var3 = null;
      var1.setline(814);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__relative_invalid_testmethod$97, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__relative_invalid_testmethod", var4);
      var3 = null;
      var1.setline(831);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__callable__TestSuite$100, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__callable__TestSuite", var4);
      var3 = null;
      var1.setline(848);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__callable__TestCase_instance$104, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__callable__TestCase_instance", var4);
      var3 = null;
      var1.setline(866);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__callable__call_staticmethod$107, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__callable__call_staticmethod", var4);
      var3 = null;
      var1.setline(890);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__callable__wrong_type$112, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__callable__wrong_type", var4);
      var3 = null;
      var1.setline(906);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_loadTestsFromNames__module_not_loaded$114, (PyObject)null);
      var1.setlocal("test_loadTestsFromNames__module_not_loaded", var4);
      var3 = null;
      var1.setline(936);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getTestCaseNames$115, (PyObject)null);
      var1.setlocal("test_getTestCaseNames", var4);
      var3 = null;
      var1.setline(949);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getTestCaseNames__no_tests$120, (PyObject)null);
      var1.setlocal("test_getTestCaseNames__no_tests", var4);
      var3 = null;
      var1.setline(965);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getTestCaseNames__not_a_TestCase$123, (PyObject)null);
      var1.setlocal("test_getTestCaseNames__not_a_TestCase", var4);
      var3 = null;
      var1.setline(981);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getTestCaseNames__inheritance$126, (PyObject)null);
      var1.setlocal("test_getTestCaseNames__inheritance", var4);
      var3 = null;
      var1.setline(1007);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testMethodPrefix__loadTestsFromTestCase$134, (PyObject)null);
      var1.setlocal("test_testMethodPrefix__loadTestsFromTestCase", var4);
      var3 = null;
      var1.setline(1028);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testMethodPrefix__loadTestsFromModule$139, (PyObject)null);
      var1.setlocal("test_testMethodPrefix__loadTestsFromModule", var4);
      var3 = null;
      var1.setline(1051);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testMethodPrefix__loadTestsFromName$144, (PyObject)null);
      var1.setlocal("test_testMethodPrefix__loadTestsFromName", var4);
      var3 = null;
      var1.setline(1074);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testMethodPrefix__loadTestsFromNames$149, (PyObject)null);
      var1.setlocal("test_testMethodPrefix__loadTestsFromNames", var4);
      var3 = null;
      var1.setline(1094);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testMethodPrefix__default_value$154, (PyObject)null);
      var1.setlocal("test_testMethodPrefix__default_value", var4);
      var3 = null;
      var1.setline(1106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__loadTestsFromTestCase$155, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__loadTestsFromTestCase", var4);
      var3 = null;
      var1.setline(1122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__loadTestsFromModule$160, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__loadTestsFromModule", var4);
      var3 = null;
      var1.setline(1140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__loadTestsFromName$165, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__loadTestsFromName", var4);
      var3 = null;
      var1.setline(1158);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__loadTestsFromNames$170, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__loadTestsFromNames", var4);
      var3 = null;
      var1.setline(1178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__getTestCaseNames$175, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__getTestCaseNames", var4);
      var3 = null;
      var1.setline(1193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__default_value$180, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__default_value", var4);
      var3 = null;
      var1.setline(1201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sortTestMethodsUsing__None$181, (PyObject)null);
      var1.setlocal("test_sortTestMethodsUsing__None", var4);
      var3 = null;
      var1.setline(1219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suiteClass__loadTestsFromTestCase$185, (PyObject)null);
      var1.setlocal("test_suiteClass__loadTestsFromTestCase", var4);
      var3 = null;
      var1.setline(1233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suiteClass__loadTestsFromModule$190, (PyObject)null);
      var1.setlocal("test_suiteClass__loadTestsFromModule", var4);
      var3 = null;
      var1.setline(1249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suiteClass__loadTestsFromName$195, (PyObject)null);
      var1.setlocal("test_suiteClass__loadTestsFromName", var4);
      var3 = null;
      var1.setline(1265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suiteClass__loadTestsFromNames$200, (PyObject)null);
      var1.setlocal("test_suiteClass__loadTestsFromNames", var4);
      var3 = null;
      var1.setline(1280);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suiteClass__default_value$205, (PyObject)null);
      var1.setlocal("test_suiteClass__default_value", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromTestCase$2(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$3);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(21);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(23);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(24);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$4, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$5, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$6, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$4(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$5(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$6(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromTestCase__no_matches$7(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$8);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(34);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(36);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(37);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, foo_bar$9, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject foo_bar$9(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromTestCase__TestSuite_subclass$10(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite")};
      PyObject var4 = Py.makeClass("NotATestCase", var3, NotATestCase$11);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(52);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;

      label19: {
         try {
            var1.setline(54);
            var1.getlocal(2).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1));
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("TypeError"))) {
               var1.setline(56);
               break label19;
            }

            throw var7;
         }

         var1.setline(58);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Should raise TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotATestCase$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(50);
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromTestCase__default_method_name$12(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$13);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(71);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(73);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, PyString.fromInterned("runTest").__getattr__("startswith").__call__(var2, var1.getlocal(2).__getattr__("testMethodPrefix")));
      var1.setline(75);
      var5 = var1.getlocal(2).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(76);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("suiteClass"));
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("runTest"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(68);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$14, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$14(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromModule__TestCase_subclass$15(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$16);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(91);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(95);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(97);
      PyList var6 = new PyList(new PyObject[]{var1.getlocal(3).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})))});
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(98);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(4)), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(89);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$17, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$17(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromModule__no_TestCase_instances$18(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(107);
      var3 = var1.getlocal(2).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(108);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("suiteClass"));
      var1.setline(109);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromModule__no_TestCase_tests$19(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(116);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$20);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(118);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(122);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(124);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__getattr__("suiteClass").__call__(var2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(117);
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromModule__not_a_module$21(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var3, MyTestCase$22);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(141);
      var3 = new PyObject[]{var1.getglobal("object")};
      PyCode var10002 = NotAModule$24;
      PyObject[] var6 = new PyObject[]{var1.getclosure(0)};
      var4 = Py.makeClass("NotAModule", var3, var10002, var6);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(144);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(145);
      var5 = var1.getlocal(2).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(147);
      PyList var7 = new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})))});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(148);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(3)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(138);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$23, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$23(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotAModule$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(142);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_2", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromModule__load_tests$25(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(154);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$26);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(158);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(160);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setderef(1, var6);
      var3 = null;
      var1.setline(161);
      var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = load_tests$28;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(165);
      var3 = var1.getlocal(3);
      var1.getlocal(1).__setattr__("load_tests", var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getlocal(4).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(169);
      var1.getderef(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(5), var1.getglobal("unittest").__getattr__("TestSuite"));
      var1.setline(170);
      var1.getderef(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)(new PyList(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getglobal("None")})));
      var1.setline(172);
      var6 = new PyList(Py.EmptyObjects);
      var1.setderef(1, var6);
      var3 = null;
      var1.setline(173);
      PyObject var10000 = var1.getlocal(4).__getattr__("loadTestsFromModule");
      var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var7 = new String[]{"use_load_tests"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(174);
      var1.getderef(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(156);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$27, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$27(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tests$28(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      var1.getderef(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(1), var1.getglobal("unittest").__getattr__("TestSuite"));
      var1.setline(163);
      var1.getderef(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(164);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromModule__faulty_load_tests$29(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(179);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, load_tests$30, (PyObject)null);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(181);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("load_tests", var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(184);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(185);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getglobal("unittest").__getattr__("TestSuite"));
      var1.setline(186);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(187);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(4)).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(189);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)PyString.fromInterned("some failure"), (PyObject)var1.getlocal(5).__getattr__("m"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tests$30(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("some failure")));
   }

   public PyObject test_loadTestsFromName__empty_name$31(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(207);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(209);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("Empty module name"));
               break label19;
            }

            throw var6;
         }

         var1.setline(211);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__malformed_name$32(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label23: {
         try {
            var1.setline(224);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("abc () //"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               var1.setline(226);
            } else {
               if (!var5.match(var1.getglobal("ImportError"))) {
                  throw var5;
               }

               var1.setline(228);
            }
            break label23;
         }

         var1.setline(230);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__unknown_module_name$33(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(240);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sdasfasfasdf"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ImportError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(242);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("No module named sdasfasfasdf"));
               break label19;
            }

            throw var6;
         }

         var1.setline(244);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise ImportError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__unknown_attr_name$34(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(256);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unittest.sdasfasfasdf"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("AttributeError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(258);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("'module' object has no attribute 'sdasfasfasdf'"));
               break label19;
            }

            throw var6;
         }

         var1.setline(260);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_unknown_name$35(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(273);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sdasfasfasdf"), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("AttributeError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(275);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("'module' object has no attribute 'sdasfasfasdf'"));
               break label19;
            }

            throw var6;
         }

         var1.setline(277);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_empty_name$36(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(294);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(296);
               break label19;
            }

            throw var5;
         }

         var1.setline(298);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_malformed_name$37(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label23: {
         try {
            var1.setline(314);
            var1.getlocal(1).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("abc () //"), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               var1.setline(316);
            } else {
               if (!var5.match(var1.getglobal("AttributeError"))) {
                  throw var5;
               }

               var1.setline(318);
            }
            break label23;
         }

         var1.setline(320);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_not_a_module$38(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var3, MyTestCase$39);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(336);
      var3 = new PyObject[]{var1.getglobal("object")};
      PyCode var10002 = NotAModule$41;
      PyObject[] var6 = new PyObject[]{var1.getclosure(0)};
      var4 = Py.makeClass("NotAModule", var3, var10002, var6);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(339);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(340);
      var5 = var1.getlocal(2).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"), (PyObject)var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(342);
      PyList var7 = new PyList(new PyObject[]{var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(343);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(3)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(333);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$40, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$40(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotAModule$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(337);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_2", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromName__relative_bad_object$42(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(354);
      var3 = var1.getglobal("object").__call__(var2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(358);
            var1.getlocal(2).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testcase_1"), (PyObject)var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("TypeError"))) {
               var1.setline(360);
               break label19;
            }

            throw var5;
         }

         var1.setline(362);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Should have raised TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_TestCase_subclass$43(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(368);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$44);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(371);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(374);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testcase_1"), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(375);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(376);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(369);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$45, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$45(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_TestSuite$46(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(384);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$47);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(387);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.getlocal(1).__setattr__("testsuite", var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testsuite"), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(391);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(393);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(385);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$48, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$48(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_testmethod$49(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(399);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$50);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(402);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(404);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(405);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testcase_1.test"), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(406);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(408);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(400);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$51, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$51(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__relative_invalid_testmethod$52(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(420);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var6, MyTestCase$53);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(423);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(425);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(427);
            var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testcase_1.testfoo"), (PyObject)var1.getlocal(1));
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("AttributeError"))) {
               var4 = var7.value;
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(429);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("type object 'MyTestCase' has no attribute 'testfoo'"));
               break label19;
            }

            throw var7;
         }

         var1.setline(431);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(421);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$54, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$54(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__callable__TestSuite$55(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(437);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(437);
      PyObject[] var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$56)));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(438);
      var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(438);
      var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$57)));
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(439);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = return_TestSuite$58;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(441);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_TestSuite", var3);
      var3 = null;
      var1.setline(443);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(444);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("return_TestSuite"), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(445);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(446);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getderef(0), var1.getderef(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$56(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$57(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_TestSuite$58(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0), var1.getderef(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromName__callable__TestCase_instance$59(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(452);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(452);
      PyObject[] var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$60)));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(453);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = return_TestCase$61;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(455);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_TestCase", var3);
      var3 = null;
      var1.setline(457);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(458);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("return_TestCase"), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(459);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(460);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getderef(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$60(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_TestCase$61(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass$62(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite")};
      PyObject var4 = Py.makeClass("SubTestSuite", var3, SubTestSuite$63);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(470);
      PyObject var5 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(471);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$64)));
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(472);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = return_TestCase$65;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(474);
      var5 = var1.getlocal(3);
      var1.getlocal(2).__setattr__("return_TestCase", var5);
      var3 = null;
      var1.setline(476);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(477);
      var5 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("suiteClass", var5);
      var3 = null;
      var1.setline(478);
      var5 = var1.getlocal(4).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("return_TestCase"), (PyObject)var1.getlocal(2));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(479);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(5), var1.getlocal(4).__getattr__("suiteClass"));
      var1.setline(480);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(5)), (PyObject)(new PyList(new PyObject[]{var1.getderef(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SubTestSuite$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(469);
      return var1.getf_locals();
   }

   public PyObject f$64(PyFrame var1, ThreadState var2) {
      var1.setline(471);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_TestCase$65(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromName__relative_testmethod_ProperSuiteClass$66(PyFrame var1, ThreadState var2) {
      var1.setline(488);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite")};
      PyObject var4 = Py.makeClass("SubTestSuite", var3, SubTestSuite$67);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(490);
      PyObject var5 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(491);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("MyTestCase", var3, MyTestCase$68);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(494);
      var5 = var1.getlocal(3);
      var1.getlocal(2).__setattr__("testcase_1", var5);
      var3 = null;
      var1.setline(496);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(497);
      var5 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("suiteClass", var5);
      var3 = null;
      var1.setline(498);
      var5 = var1.getlocal(4).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testcase_1.test"), (PyObject)var1.getlocal(2));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(499);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(5), var1.getlocal(4).__getattr__("suiteClass"));
      var1.setline(501);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(5)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SubTestSuite$67(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(489);
      return var1.getf_locals();
   }

   public PyObject MyTestCase$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(492);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$69, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$69(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromName__callable__wrong_type$70(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(509);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, return_wrong$71, (PyObject)null);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(511);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_wrong", var3);
      var3 = null;
      var1.setline(513);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(515);
            var1.getlocal(3).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("return_wrong"), (PyObject)var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var7 = Py.setException(var4, var1);
            if (var7.match(var1.getglobal("TypeError"))) {
               var1.setline(517);
               break label19;
            }

            throw var7;
         }

         var1.setline(519);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject return_wrong$71(PyFrame var1, ThreadState var2) {
      var1.setline(510);
      PyInteger var3 = Py.newInteger(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromName__module_not_loaded$72(PyFrame var1, ThreadState var2) {
      var1.setline(527);
      PyString var3 = PyString.fromInterned("unittest.test.dummy");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(528);
      var1.getglobal("sys").__getattr__("modules").__getattr__("pop").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      var1.setline(530);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var3 = null;

      PyObject var10000;
      PyObject var4;
      try {
         var1.setline(532);
         var4 = var1.getlocal(2).__getattr__("loadTestsFromName").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(534);
         var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("suiteClass"));
         var1.setline(535);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(Py.EmptyObjects)));
         var1.setline(538);
         var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("modules"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(540);
         var4 = var1.getlocal(1);
         var10000 = var4._in(var1.getglobal("sys").__getattr__("modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(541);
            var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(1));
         }

         throw (Throwable)var5;
      }

      var1.setline(540);
      var4 = var1.getlocal(1);
      var10000 = var4._in(var1.getglobal("sys").__getattr__("modules"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(541);
         var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__empty_name_list$73(PyFrame var1, ThreadState var2) {
      var1.setline(554);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(556);
      var3 = var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(557);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getattr__("suiteClass"));
      var1.setline(558);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(2)), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_empty_name_list$74(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(571);
      var3 = var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)var1.getglobal("unittest"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(572);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getattr__("suiteClass"));
      var1.setline(573);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(2)), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__empty_name$75(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(585);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("")})));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(587);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("Empty module name"));
               break label19;
            }

            throw var6;
         }

         var1.setline(589);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__malformed_name$76(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label23: {
         try {
            var1.setline(602);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("abc () //")})));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               var1.setline(604);
            } else {
               if (!var5.match(var1.getglobal("ImportError"))) {
                  throw var5;
               }

               var1.setline(606);
            }
            break label23;
         }

         var1.setline(608);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__unknown_module_name$77(PyFrame var1, ThreadState var2) {
      var1.setline(617);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(620);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("sdasfasfasdf")})));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ImportError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(622);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("No module named sdasfasfasdf"));
               break label19;
            }

            throw var6;
         }

         var1.setline(624);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise ImportError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__unknown_attr_name$78(PyFrame var1, ThreadState var2) {
      var1.setline(633);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(636);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("unittest.sdasfasfasdf"), PyString.fromInterned("unittest")})));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("AttributeError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(638);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("'module' object has no attribute 'sdasfasfasdf'"));
               break label19;
            }

            throw var6;
         }

         var1.setline(640);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__unknown_name_relative_1$79(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(655);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("sdasfasfasdf")})), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("AttributeError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(657);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("'module' object has no attribute 'sdasfasfasdf'"));
               break label19;
            }

            throw var6;
         }

         var1.setline(659);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__unknown_name_relative_2$80(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(674);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("TestCase"), PyString.fromInterned("sdasfasfasdf")})), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("AttributeError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(676);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2)), (PyObject)PyString.fromInterned("'module' object has no attribute 'sdasfasfasdf'"));
               break label19;
            }

            throw var6;
         }

         var1.setline(678);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromName failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_empty_name$81(PyFrame var1, ThreadState var2) {
      var1.setline(692);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(695);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("")})), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(697);
               break label19;
            }

            throw var5;
         }

         var1.setline(699);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_malformed_name$82(PyFrame var1, ThreadState var2) {
      var1.setline(710);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label23: {
         try {
            var1.setline(714);
            var1.getlocal(1).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("abc () //")})), (PyObject)var1.getglobal("unittest"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(716);
            } else {
               if (!var5.match(var1.getglobal("ValueError"))) {
                  throw var5;
               }

               var1.setline(718);
            }
            break label23;
         }

         var1.setline(720);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_not_a_module$83(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var3, MyTestCase$84);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(734);
      var3 = new PyObject[]{var1.getglobal("object")};
      PyCode var10002 = NotAModule$86;
      PyObject[] var6 = new PyObject[]{var1.getclosure(0)};
      var4 = Py.makeClass("NotAModule", var3, var10002, var6);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(737);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(738);
      var5 = var1.getlocal(2).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("test_2")})), (PyObject)var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(740);
      PyList var7 = new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})))});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(741);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(3)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$84(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(731);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$85, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$85(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotAModule$86(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(735);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_2", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_loadTestsFromNames__relative_bad_object$87(PyFrame var1, ThreadState var2) {
      var1.setline(751);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(752);
      var3 = var1.getglobal("object").__call__(var2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(754);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(756);
            var1.getlocal(2).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("testcase_1")})), (PyObject)var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("TypeError"))) {
               var1.setline(758);
               break label19;
            }

            throw var5;
         }

         var1.setline(760);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Should have raised TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_TestCase_subclass$88(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(766);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$89);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(769);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(771);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(772);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("testcase_1")})), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(773);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(775);
      var3 = var1.getlocal(3).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(776);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(767);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$90, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$90(PyFrame var1, ThreadState var2) {
      var1.setline(768);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_TestSuite$91(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(782);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$92);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(785);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.getlocal(1).__setattr__("testsuite", var3);
      var3 = null;
      var1.setline(787);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(788);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("testsuite")})), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(789);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(791);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__getattr__("testsuite")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$92(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(783);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$93, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$93(PyFrame var1, ThreadState var2) {
      var1.setline(784);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_testmethod$94(PyFrame var1, ThreadState var2) {
      var1.setline(796);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(797);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var5, MyTestCase$95);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(800);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(802);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(803);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("testcase_1.test")})), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(804);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(806);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(807);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(798);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$96, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$96(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__relative_invalid_testmethod$97(PyFrame var1, ThreadState var2) {
      var1.setline(815);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(816);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("MyTestCase", var6, MyTestCase$98);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(819);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("testcase_1", var3);
      var3 = null;
      var1.setline(821);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(823);
            var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("testcase_1.testfoo")})), (PyObject)var1.getlocal(1));
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("AttributeError"))) {
               var4 = var7.value;
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(825);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("type object 'MyTestCase' has no attribute 'testfoo'"));
               break label19;
            }

            throw var7;
         }

         var1.setline(827);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise AttributeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyTestCase$98(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(817);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$99, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$99(PyFrame var1, ThreadState var2) {
      var1.setline(818);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_loadTestsFromNames__callable__TestSuite$100(PyFrame var1, ThreadState var2) {
      var1.setline(832);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(833);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(833);
      PyObject[] var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$101)));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(834);
      var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(834);
      var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$102)));
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(835);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = return_TestSuite$103;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(837);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_TestSuite", var3);
      var3 = null;
      var1.setline(839);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(840);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("return_TestSuite")})), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(841);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(843);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0), var1.getderef(1)})));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(844);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$101(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$102(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_TestSuite$103(PyFrame var1, ThreadState var2) {
      var1.setline(836);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0), var1.getderef(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromNames__callable__TestCase_instance$104(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(850);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(850);
      PyObject[] var4 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$105)));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(851);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = return_TestCase$106;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(853);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_TestCase", var3);
      var3 = null;
      var1.setline(855);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(856);
      var3 = var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("return_TestCase")})), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(857);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getlocal(3).__getattr__("suiteClass"));
      var1.setline(859);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0)})));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(860);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(4)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$105(PyFrame var1, ThreadState var2) {
      var1.setline(850);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_TestCase$106(PyFrame var1, ThreadState var2) {
      var1.setline(852);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromNames__callable__call_staticmethod$107(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(868);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test1", var5, Test1$108);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(872);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(873);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Foo", var5, Foo$110);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(877);
      var3 = var1.getlocal(3);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(879);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(880);
      var3 = var1.getlocal(4).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Foo.foo")})), (PyObject)var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(881);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(5), var1.getlocal(4).__getattr__("suiteClass"));
      var1.setline(883);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(0)})));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(884);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(5)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(6)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$108(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(869);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$109, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$109(PyFrame var1, ThreadState var2) {
      var1.setline(870);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$110(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(874);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = foo$111;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("foo", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject foo$111(PyFrame var1, ThreadState var2) {
      var1.setline(876);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromNames__callable__wrong_type$112(PyFrame var1, ThreadState var2) {
      var1.setline(891);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(892);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, return_wrong$113, (PyObject)null);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(894);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("return_wrong", var3);
      var3 = null;
      var1.setline(896);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(898);
            var1.getlocal(3).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("return_wrong")})), (PyObject)var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var7 = Py.setException(var4, var1);
            if (var7.match(var1.getglobal("TypeError"))) {
               var1.setline(900);
               break label19;
            }

            throw var7;
         }

         var1.setline(902);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestLoader.loadTestsFromNames failed to raise TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject return_wrong$113(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      PyInteger var3 = Py.newInteger(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_loadTestsFromNames__module_not_loaded$114(PyFrame var1, ThreadState var2) {
      var1.setline(910);
      PyString var3 = PyString.fromInterned("unittest.test.dummy");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(911);
      var1.getglobal("sys").__getattr__("modules").__getattr__("pop").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      var1.setline(913);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var3 = null;

      PyObject var10000;
      PyObject var4;
      try {
         var1.setline(915);
         var4 = var1.getlocal(2).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(917);
         var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("suiteClass"));
         var1.setline(918);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2)})));
         var1.setline(921);
         var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("modules"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(923);
         var4 = var1.getlocal(1);
         var10000 = var4._in(var1.getglobal("sys").__getattr__("modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(924);
            var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(1));
         }

         throw (Throwable)var5;
      }

      var1.setline(923);
      var4 = var1.getlocal(1);
      var10000 = var4._in(var1.getglobal("sys").__getattr__("modules"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(924);
         var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getTestCaseNames$115(PyFrame var1, ThreadState var2) {
      var1.setline(937);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$116);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(942);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(944);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(1)), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("test_1"), PyString.fromInterned("test_2")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$116(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(938);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$117, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(939);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$118, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(940);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foobar$119, (PyObject)null);
      var1.setlocal("foobar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$117(PyFrame var1, ThreadState var2) {
      var1.setline(938);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$118(PyFrame var1, ThreadState var2) {
      var1.setline(939);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foobar$119(PyFrame var1, ThreadState var2) {
      var1.setline(940);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getTestCaseNames__no_tests$120(PyFrame var1, ThreadState var2) {
      var1.setline(950);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$121);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(953);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(955);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(1)), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$121(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(951);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, foobar$122, (PyObject)null);
      var1.setlocal("foobar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject foobar$122(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getTestCaseNames__not_a_TestCase$123(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyObject[] var3 = new PyObject[]{var1.getglobal("int")};
      PyObject var4 = Py.makeClass("BadCase", var3, BadCase$124);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(970);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(971);
      var5 = var1.getlocal(2).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(973);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("test_foo")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BadCase$124(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(967);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_foo$125, (PyObject)null);
      var1.setlocal("test_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_foo$125(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getTestCaseNames__inheritance$126(PyFrame var1, ThreadState var2) {
      var1.setline(982);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestP", var3, TestP$127);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(987);
      var3 = new PyObject[]{var1.getlocal(1)};
      var4 = Py.makeClass("TestC", var3, TestC$131);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(991);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(993);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("test_1"), PyString.fromInterned("test_2"), PyString.fromInterned("test_3")});
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(994);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(2)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestP$127(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(983);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$128, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(984);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$129, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(985);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foobar$130, (PyObject)null);
      var1.setlocal("foobar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$128(PyFrame var1, ThreadState var2) {
      var1.setline(983);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$129(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foobar$130(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestC$131(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(988);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$132, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(989);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_3$133, (PyObject)null);
      var1.setlocal("test_3", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$132(PyFrame var1, ThreadState var2) {
      var1.setline(988);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_3$133(PyFrame var1, ThreadState var2) {
      var1.setline(989);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testMethodPrefix__loadTestsFromTestCase$134(PyFrame var1, ThreadState var2) {
      var1.setline(1008);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$135);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1013);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo_bar"))})));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1014);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1016);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(1017);
      PyString var6 = PyString.fromInterned("foo");
      var1.getlocal(4).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1018);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.setline(1020);
      var6 = PyString.fromInterned("test");
      var1.getlocal(4).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1021);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1009);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$136, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1010);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$137, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1011);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$138, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$136(PyFrame var1, ThreadState var2) {
      var1.setline(1009);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$137(PyFrame var1, ThreadState var2) {
      var1.setline(1010);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$138(PyFrame var1, ThreadState var2) {
      var1.setline(1011);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testMethodPrefix__loadTestsFromModule$139(PyFrame var1, ThreadState var2) {
      var1.setline(1029);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1030);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$140);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1034);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1036);
      PyList var6 = new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo_bar"))})))});
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1037);
      var6 = new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})))});
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1039);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1040);
      PyString var7 = PyString.fromInterned("foo");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var7);
      var3 = null;
      var1.setline(1041);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1))), var1.getlocal(3));
      var1.setline(1043);
      var7 = PyString.fromInterned("test");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var7);
      var3 = null;
      var1.setline(1044);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1))), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$140(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1031);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$141, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1032);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$142, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1033);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$143, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$141(PyFrame var1, ThreadState var2) {
      var1.setline(1031);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$142(PyFrame var1, ThreadState var2) {
      var1.setline(1032);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$143(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testMethodPrefix__loadTestsFromName$144(PyFrame var1, ThreadState var2) {
      var1.setline(1052);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1053);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$145);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1057);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1059);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo_bar"))})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1060);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1062);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1063);
      PyString var6 = PyString.fromInterned("foo");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1064);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Foo"), (PyObject)var1.getlocal(1)), var1.getlocal(3));
      var1.setline(1066);
      var6 = PyString.fromInterned("test");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1067);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Foo"), (PyObject)var1.getlocal(1)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$145(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1054);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$146, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1055);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$147, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1056);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$148, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$146(PyFrame var1, ThreadState var2) {
      var1.setline(1054);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$147(PyFrame var1, ThreadState var2) {
      var1.setline(1055);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$148(PyFrame var1, ThreadState var2) {
      var1.setline(1056);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testMethodPrefix__loadTestsFromNames$149(PyFrame var1, ThreadState var2) {
      var1.setline(1075);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1076);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$150);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1080);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1082);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo_bar"))})))})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1083);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1084);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4)})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1086);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1087);
      PyString var6 = PyString.fromInterned("foo");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1088);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Foo")})), (PyObject)var1.getlocal(1)), var1.getlocal(3));
      var1.setline(1090);
      var6 = PyString.fromInterned("test");
      var1.getlocal(5).__setattr__((String)"testMethodPrefix", var6);
      var3 = null;
      var1.setline(1091);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Foo")})), (PyObject)var1.getlocal(1)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$150(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1077);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$151, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1078);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$152, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1079);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$153, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$151(PyFrame var1, ThreadState var2) {
      var1.setline(1077);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$152(PyFrame var1, ThreadState var2) {
      var1.setline(1078);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$153(PyFrame var1, ThreadState var2) {
      var1.setline(1079);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testMethodPrefix__default_value$154(PyFrame var1, ThreadState var2) {
      var1.setline(1095);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1096);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(1).__getattr__("testMethodPrefix");
      PyObject var10002 = var3._eq(PyString.fromInterned("test"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__loadTestsFromTestCase$155(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, reversed_cmp$156, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1110);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$157);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1114);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1115);
      var6 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("sortTestMethodsUsing", var6);
      var3 = null;
      var1.setline(1117);
      var6 = var1.getlocal(3).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))})));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1118);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(2)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reversed_cmp$156(PyFrame var1, ThreadState var2) {
      var1.setline(1108);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Foo$157(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1111);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$158, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$159, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$158(PyFrame var1, ThreadState var2) {
      var1.setline(1111);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$159(PyFrame var1, ThreadState var2) {
      var1.setline(1112);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__loadTestsFromModule$160(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, reversed_cmp$161, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1126);
      PyObject var6 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1127);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$162);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1130);
      var6 = var1.getlocal(3);
      var1.getlocal(2).__setattr__("Foo", var6);
      var3 = null;
      var1.setline(1132);
      var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1133);
      var6 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("sortTestMethodsUsing", var6);
      var3 = null;
      var1.setline(1135);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(4).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2")), var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))})))});
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(1136);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(2))), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reversed_cmp$161(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Foo$162(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1128);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$163, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$164, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$163(PyFrame var1, ThreadState var2) {
      var1.setline(1128);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$164(PyFrame var1, ThreadState var2) {
      var1.setline(1129);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__loadTestsFromName$165(PyFrame var1, ThreadState var2) {
      var1.setline(1141);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, reversed_cmp$166, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1144);
      PyObject var6 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1145);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$167);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1148);
      var6 = var1.getlocal(3);
      var1.getlocal(2).__setattr__("Foo", var6);
      var3 = null;
      var1.setline(1150);
      var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1151);
      var6 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("sortTestMethodsUsing", var6);
      var3 = null;
      var1.setline(1153);
      var6 = var1.getlocal(4).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2")), var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))})));
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1154);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Foo"), (PyObject)var1.getlocal(2)), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reversed_cmp$166(PyFrame var1, ThreadState var2) {
      var1.setline(1142);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Foo$167(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1146);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$168, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1147);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$169, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$168(PyFrame var1, ThreadState var2) {
      var1.setline(1146);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$169(PyFrame var1, ThreadState var2) {
      var1.setline(1147);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__loadTestsFromNames$170(PyFrame var1, ThreadState var2) {
      var1.setline(1159);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, reversed_cmp$171, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1162);
      PyObject var6 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1163);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$172);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1166);
      var6 = var1.getlocal(3);
      var1.getlocal(2).__setattr__("Foo", var6);
      var3 = null;
      var1.setline(1168);
      var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1169);
      var6 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("sortTestMethodsUsing", var6);
      var3 = null;
      var1.setline(1171);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(4).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2")), var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))})))});
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(1172);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Foo")})), (PyObject)var1.getlocal(2))), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reversed_cmp$171(PyFrame var1, ThreadState var2) {
      var1.setline(1160);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Foo$172(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1164);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$173, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$174, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$173(PyFrame var1, ThreadState var2) {
      var1.setline(1164);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$174(PyFrame var1, ThreadState var2) {
      var1.setline(1165);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__getTestCaseNames$175(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, reversed_cmp$176, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(1182);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$177);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1186);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1187);
      var6 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("sortTestMethodsUsing", var6);
      var3 = null;
      var1.setline(1189);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("test_2"), PyString.fromInterned("test_1")});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(1190);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(2)), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reversed_cmp$176(PyFrame var1, ThreadState var2) {
      var1.setline(1180);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__neg__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Foo$177(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1183);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$178, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$179, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$178(PyFrame var1, ThreadState var2) {
      var1.setline(1183);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$179(PyFrame var1, ThreadState var2) {
      var1.setline(1184);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__default_value$180(PyFrame var1, ThreadState var2) {
      var1.setline(1194);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1195);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(1).__getattr__("sortTestMethodsUsing");
      PyObject var10002 = var3._is(var1.getglobal("cmp"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sortTestMethodsUsing__None$181(PyFrame var1, ThreadState var2) {
      var1.setline(1202);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$182);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1206);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1207);
      var5 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("sortTestMethodsUsing", var5);
      var3 = null;
      var1.setline(1209);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("test_2"), PyString.fromInterned("test_1")});
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1210);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(2).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(1))), var1.getglobal("set").__call__(var2, var1.getlocal(3)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$182(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1203);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$183, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$184, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$183(PyFrame var1, ThreadState var2) {
      var1.setline(1203);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$184(PyFrame var1, ThreadState var2) {
      var1.setline(1204);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suiteClass__loadTestsFromTestCase$185(PyFrame var1, ThreadState var2) {
      var1.setline(1220);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$186);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1225);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1227);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1228);
      var6 = var1.getglobal("list");
      var1.getlocal(3).__setattr__("suiteClass", var6);
      var3 = null;
      var1.setline(1229);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$186(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1221);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$187, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1222);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$188, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$189, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$187(PyFrame var1, ThreadState var2) {
      var1.setline(1221);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$188(PyFrame var1, ThreadState var2) {
      var1.setline(1222);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$189(PyFrame var1, ThreadState var2) {
      var1.setline(1223);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suiteClass__loadTestsFromModule$190(PyFrame var1, ThreadState var2) {
      var1.setline(1234);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1235);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$191);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1239);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1241);
      PyList var6 = new PyList(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})});
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1243);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1244);
      var3 = var1.getglobal("list");
      var1.getlocal(4).__setattr__("suiteClass", var3);
      var3 = null;
      var1.setline(1245);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$191(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1236);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$192, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$193, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$194, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$192(PyFrame var1, ThreadState var2) {
      var1.setline(1236);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$193(PyFrame var1, ThreadState var2) {
      var1.setline(1237);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$194(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suiteClass__loadTestsFromName$195(PyFrame var1, ThreadState var2) {
      var1.setline(1250);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1251);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$196);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1255);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1257);
      PyList var6 = new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))});
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1259);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1260);
      var3 = var1.getglobal("list");
      var1.getlocal(4).__setattr__("suiteClass", var3);
      var3 = null;
      var1.setline(1261);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Foo"), (PyObject)var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$196(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1252);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$197, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$198, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$199, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$197(PyFrame var1, ThreadState var2) {
      var1.setline(1252);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$198(PyFrame var1, ThreadState var2) {
      var1.setline(1253);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$199(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suiteClass__loadTestsFromNames$200(PyFrame var1, ThreadState var2) {
      var1.setline(1266);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("m"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1267);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$201);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(1271);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("Foo", var3);
      var3 = null;
      var1.setline(1273);
      PyList var6 = new PyList(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})});
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1275);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1276);
      var3 = var1.getglobal("list");
      var1.getlocal(4).__setattr__("suiteClass", var3);
      var3 = null;
      var1.setline(1277);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("loadTestsFromNames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Foo")})), (PyObject)var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$201(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1268);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$202, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(1269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$203, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(1270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, foo_bar$204, (PyObject)null);
      var1.setlocal("foo_bar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$202(PyFrame var1, ThreadState var2) {
      var1.setline(1268);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$203(PyFrame var1, ThreadState var2) {
      var1.setline(1269);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo_bar$204(PyFrame var1, ThreadState var2) {
      var1.setline(1270);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suiteClass__default_value$205(PyFrame var1, ThreadState var2) {
      var1.setline(1281);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1282);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(1).__getattr__("suiteClass");
      PyObject var10002 = var3._is(var1.getglobal("unittest").__getattr__("TestSuite"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_loader$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_TestLoader$1 = Py.newCode(0, var2, var1, "Test_TestLoader", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Foo", "tests", "loader"};
      test_loadTestsFromTestCase$2 = Py.newCode(1, var2, var1, "test_loadTestsFromTestCase", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$3 = Py.newCode(0, var2, var1, "Foo", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$4 = Py.newCode(1, var2, var1, "test_1", 17, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$5 = Py.newCode(1, var2, var1, "test_2", 18, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$6 = Py.newCode(1, var2, var1, "foo_bar", 19, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "empty_suite", "loader"};
      test_loadTestsFromTestCase__no_matches$7 = Py.newCode(1, var2, var1, "test_loadTestsFromTestCase__no_matches", 30, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$8 = Py.newCode(0, var2, var1, "Foo", 31, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      foo_bar$9 = Py.newCode(1, var2, var1, "foo_bar", 32, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "NotATestCase", "loader"};
      test_loadTestsFromTestCase__TestSuite_subclass$10 = Py.newCode(1, var2, var1, "test_loadTestsFromTestCase__TestSuite_subclass", 48, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NotATestCase$11 = Py.newCode(0, var2, var1, "NotATestCase", 49, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Foo", "loader", "suite"};
      test_loadTestsFromTestCase__default_method_name$12 = Py.newCode(1, var2, var1, "test_loadTestsFromTestCase__default_method_name", 66, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$13 = Py.newCode(0, var2, var1, "Foo", 67, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$14 = Py.newCode(1, var2, var1, "runTest", 68, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite", "expected"};
      test_loadTestsFromModule__TestCase_subclass$15 = Py.newCode(1, var2, var1, "test_loadTestsFromModule__TestCase_subclass", 86, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$16 = Py.newCode(0, var2, var1, "MyTestCase", 88, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$17 = Py.newCode(1, var2, var1, "test", 89, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "loader", "suite"};
      test_loadTestsFromModule__no_TestCase_instances$18 = Py.newCode(1, var2, var1, "test_loadTestsFromModule__no_TestCase_instances", 103, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromModule__no_TestCase_tests$19 = Py.newCode(1, var2, var1, "test_loadTestsFromModule__no_TestCase_tests", 114, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$20 = Py.newCode(0, var2, var1, "MyTestCase", 116, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "NotAModule", "loader", "suite", "reference", "MyTestCase"};
      String[] var10001 = var2;
      test_loader$py var10007 = self;
      var2 = new String[]{"MyTestCase"};
      test_loadTestsFromModule__not_a_module$21 = Py.newCode(1, var10001, var1, "test_loadTestsFromModule__not_a_module", 136, false, false, var10007, 21, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      MyTestCase$22 = Py.newCode(0, var2, var1, "MyTestCase", 137, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$23 = Py.newCode(1, var2, var1, "test", 138, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"MyTestCase"};
      NotAModule$24 = Py.newCode(0, var10001, var1, "NotAModule", 141, false, false, var10007, 24, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self", "m", "MyTestCase", "load_tests", "loader", "suite", "load_tests_args"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "load_tests_args"};
      test_loadTestsFromModule__load_tests$25 = Py.newCode(1, var10001, var1, "test_loadTestsFromModule__load_tests", 153, false, false, var10007, 25, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      MyTestCase$26 = Py.newCode(0, var2, var1, "MyTestCase", 155, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$27 = Py.newCode(1, var2, var1, "test", 156, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"loader", "tests", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "load_tests_args"};
      load_tests$28 = Py.newCode(3, var10001, var1, "load_tests", 161, false, false, var10007, 28, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "m", "load_tests", "loader", "suite", "test"};
      test_loadTestsFromModule__faulty_load_tests$29 = Py.newCode(1, var2, var1, "test_loadTestsFromModule__faulty_load_tests", 176, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"loader", "tests", "pattern"};
      load_tests$30 = Py.newCode(3, var2, var1, "load_tests", 179, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromName__empty_name$31 = Py.newCode(1, var2, var1, "test_loadTestsFromName__empty_name", 203, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromName__malformed_name$32 = Py.newCode(1, var2, var1, "test_loadTestsFromName__malformed_name", 219, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromName__unknown_module_name$33 = Py.newCode(1, var2, var1, "test_loadTestsFromName__unknown_module_name", 236, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromName__unknown_attr_name$34 = Py.newCode(1, var2, var1, "test_loadTestsFromName__unknown_attr_name", 252, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromName__relative_unknown_name$35 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_unknown_name", 269, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromName__relative_empty_name$36 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_empty_name", 290, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromName__relative_malformed_name$37 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_malformed_name", 309, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "NotAModule", "loader", "suite", "reference", "MyTestCase"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"MyTestCase"};
      test_loadTestsFromName__relative_not_a_module$38 = Py.newCode(1, var10001, var1, "test_loadTestsFromName__relative_not_a_module", 331, false, false, var10007, 38, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      MyTestCase$39 = Py.newCode(0, var2, var1, "MyTestCase", 332, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$40 = Py.newCode(1, var2, var1, "test", 333, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"MyTestCase"};
      NotAModule$41 = Py.newCode(0, var10001, var1, "NotAModule", 336, false, false, var10007, 41, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self", "m", "loader"};
      test_loadTestsFromName__relative_bad_object$42 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_bad_object", 352, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromName__relative_TestCase_subclass$43 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_TestCase_subclass", 366, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$44 = Py.newCode(0, var2, var1, "MyTestCase", 368, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$45 = Py.newCode(1, var2, var1, "test", 369, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromName__relative_TestSuite$46 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_TestSuite", 382, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$47 = Py.newCode(0, var2, var1, "MyTestCase", 384, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$48 = Py.newCode(1, var2, var1, "test", 385, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromName__relative_testmethod$49 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_testmethod", 397, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$50 = Py.newCode(0, var2, var1, "MyTestCase", 399, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$51 = Py.newCode(1, var2, var1, "test", 400, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "e"};
      test_loadTestsFromName__relative_invalid_testmethod$52 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_invalid_testmethod", 418, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$53 = Py.newCode(0, var2, var1, "MyTestCase", 420, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$54 = Py.newCode(1, var2, var1, "test", 421, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "return_TestSuite", "loader", "suite", "testcase_1", "testcase_2"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1", "testcase_2"};
      test_loadTestsFromName__callable__TestSuite$55 = Py.newCode(1, var10001, var1, "test_loadTestsFromName__callable__TestSuite", 435, false, false, var10007, 55, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      f$56 = Py.newCode(0, var2, var1, "<lambda>", 437, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$57 = Py.newCode(0, var2, var1, "<lambda>", 438, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1", "testcase_2"};
      return_TestSuite$58 = Py.newCode(0, var10001, var1, "return_TestSuite", 439, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "m", "return_TestCase", "loader", "suite", "testcase_1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      test_loadTestsFromName__callable__TestCase_instance$59 = Py.newCode(1, var10001, var1, "test_loadTestsFromName__callable__TestCase_instance", 450, false, false, var10007, 59, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      f$60 = Py.newCode(0, var2, var1, "<lambda>", 452, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      return_TestCase$61 = Py.newCode(0, var10001, var1, "return_TestCase", 453, false, false, var10007, 61, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "SubTestSuite", "m", "return_TestCase", "loader", "suite", "testcase_1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass$62 = Py.newCode(1, var10001, var1, "test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass", 467, false, false, var10007, 62, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      SubTestSuite$63 = Py.newCode(0, var2, var1, "SubTestSuite", 468, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      f$64 = Py.newCode(0, var2, var1, "<lambda>", 471, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      return_TestCase$65 = Py.newCode(0, var10001, var1, "return_TestCase", 472, false, false, var10007, 65, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "SubTestSuite", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromName__relative_testmethod_ProperSuiteClass$66 = Py.newCode(1, var2, var1, "test_loadTestsFromName__relative_testmethod_ProperSuiteClass", 487, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SubTestSuite$67 = Py.newCode(0, var2, var1, "SubTestSuite", 488, false, false, self, 67, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MyTestCase$68 = Py.newCode(0, var2, var1, "MyTestCase", 491, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$69 = Py.newCode(1, var2, var1, "test", 492, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "return_wrong", "loader"};
      test_loadTestsFromName__callable__wrong_type$70 = Py.newCode(1, var2, var1, "test_loadTestsFromName__callable__wrong_type", 507, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      return_wrong$71 = Py.newCode(0, var2, var1, "return_wrong", 509, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module_name", "loader", "suite"};
      test_loadTestsFromName__module_not_loaded$72 = Py.newCode(1, var2, var1, "test_loadTestsFromName__module_not_loaded", 523, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "suite"};
      test_loadTestsFromNames__empty_name_list$73 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__empty_name_list", 553, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "suite"};
      test_loadTestsFromNames__relative_empty_name_list$74 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_empty_name_list", 568, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromNames__empty_name$75 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__empty_name", 581, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromNames__malformed_name$76 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__malformed_name", 597, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromNames__unknown_module_name$77 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__unknown_module_name", 616, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromNames__unknown_attr_name$78 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__unknown_attr_name", 632, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromNames__unknown_name_relative_1$79 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__unknown_name_relative_1", 651, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "e"};
      test_loadTestsFromNames__unknown_name_relative_2$80 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__unknown_name_relative_2", 670, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromNames__relative_empty_name$81 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_empty_name", 691, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_loadTestsFromNames__relative_malformed_name$82 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_malformed_name", 709, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "NotAModule", "loader", "suite", "reference", "MyTestCase"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"MyTestCase"};
      test_loadTestsFromNames__relative_not_a_module$83 = Py.newCode(1, var10001, var1, "test_loadTestsFromNames__relative_not_a_module", 729, false, false, var10007, 83, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      MyTestCase$84 = Py.newCode(0, var2, var1, "MyTestCase", 730, false, false, self, 84, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$85 = Py.newCode(1, var2, var1, "test", 731, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"MyTestCase"};
      NotAModule$86 = Py.newCode(0, var10001, var1, "NotAModule", 734, false, false, var10007, 86, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self", "m", "loader"};
      test_loadTestsFromNames__relative_bad_object$87 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_bad_object", 750, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite", "expected"};
      test_loadTestsFromNames__relative_TestCase_subclass$88 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_TestCase_subclass", 764, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$89 = Py.newCode(0, var2, var1, "MyTestCase", 766, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$90 = Py.newCode(1, var2, var1, "test", 767, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite"};
      test_loadTestsFromNames__relative_TestSuite$91 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_TestSuite", 780, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$92 = Py.newCode(0, var2, var1, "MyTestCase", 782, false, false, self, 92, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$93 = Py.newCode(1, var2, var1, "test", 783, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "suite", "ref_suite"};
      test_loadTestsFromNames__relative_testmethod$94 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_testmethod", 795, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$95 = Py.newCode(0, var2, var1, "MyTestCase", 797, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$96 = Py.newCode(1, var2, var1, "test", 798, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "MyTestCase", "loader", "e"};
      test_loadTestsFromNames__relative_invalid_testmethod$97 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__relative_invalid_testmethod", 814, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyTestCase$98 = Py.newCode(0, var2, var1, "MyTestCase", 816, false, false, self, 98, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$99 = Py.newCode(1, var2, var1, "test", 817, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "return_TestSuite", "loader", "suite", "expected", "testcase_1", "testcase_2"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1", "testcase_2"};
      test_loadTestsFromNames__callable__TestSuite$100 = Py.newCode(1, var10001, var1, "test_loadTestsFromNames__callable__TestSuite", 831, false, false, var10007, 100, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      f$101 = Py.newCode(0, var2, var1, "<lambda>", 833, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$102 = Py.newCode(0, var2, var1, "<lambda>", 834, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1", "testcase_2"};
      return_TestSuite$103 = Py.newCode(0, var10001, var1, "return_TestSuite", 835, false, false, var10007, 103, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "m", "return_TestCase", "loader", "suite", "ref_suite", "testcase_1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      test_loadTestsFromNames__callable__TestCase_instance$104 = Py.newCode(1, var10001, var1, "test_loadTestsFromNames__callable__TestCase_instance", 848, false, false, var10007, 104, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      f$105 = Py.newCode(0, var2, var1, "<lambda>", 850, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      return_TestCase$106 = Py.newCode(0, var10001, var1, "return_TestCase", 851, false, false, var10007, 106, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "m", "Test1", "Foo", "loader", "suite", "ref_suite", "testcase_1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      test_loadTestsFromNames__callable__call_staticmethod$107 = Py.newCode(1, var10001, var1, "test_loadTestsFromNames__callable__call_staticmethod", 866, false, false, var10007, 107, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test1$108 = Py.newCode(0, var2, var1, "Test1", 868, false, false, self, 108, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$109 = Py.newCode(1, var2, var1, "test", 869, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$110 = Py.newCode(0, var2, var1, "Foo", 873, false, false, self, 110, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"testcase_1"};
      foo$111 = Py.newCode(0, var10001, var1, "foo", 874, false, false, var10007, 111, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "m", "return_wrong", "loader"};
      test_loadTestsFromNames__callable__wrong_type$112 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__callable__wrong_type", 890, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      return_wrong$113 = Py.newCode(0, var2, var1, "return_wrong", 892, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module_name", "loader", "suite"};
      test_loadTestsFromNames__module_not_loaded$114 = Py.newCode(1, var2, var1, "test_loadTestsFromNames__module_not_loaded", 906, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "loader"};
      test_getTestCaseNames$115 = Py.newCode(1, var2, var1, "test_getTestCaseNames", 936, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$116 = Py.newCode(0, var2, var1, "Test", 937, false, false, self, 116, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$117 = Py.newCode(1, var2, var1, "test_1", 938, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$118 = Py.newCode(1, var2, var1, "test_2", 939, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foobar$119 = Py.newCode(1, var2, var1, "foobar", 940, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "loader"};
      test_getTestCaseNames__no_tests$120 = Py.newCode(1, var2, var1, "test_getTestCaseNames__no_tests", 949, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$121 = Py.newCode(0, var2, var1, "Test", 950, false, false, self, 121, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      foobar$122 = Py.newCode(1, var2, var1, "foobar", 951, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "BadCase", "loader", "names"};
      test_getTestCaseNames__not_a_TestCase$123 = Py.newCode(1, var2, var1, "test_getTestCaseNames__not_a_TestCase", 965, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BadCase$124 = Py.newCode(0, var2, var1, "BadCase", 966, false, false, self, 124, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_foo$125 = Py.newCode(1, var2, var1, "test_foo", 967, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "TestP", "TestC", "loader", "names"};
      test_getTestCaseNames__inheritance$126 = Py.newCode(1, var2, var1, "test_getTestCaseNames__inheritance", 981, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestP$127 = Py.newCode(0, var2, var1, "TestP", 982, false, false, self, 127, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$128 = Py.newCode(1, var2, var1, "test_1", 983, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$129 = Py.newCode(1, var2, var1, "test_2", 984, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foobar$130 = Py.newCode(1, var2, var1, "foobar", 985, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestC$131 = Py.newCode(0, var2, var1, "TestC", 987, false, false, self, 131, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$132 = Py.newCode(1, var2, var1, "test_1", 988, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_3$133 = Py.newCode(1, var2, var1, "test_3", 989, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "tests_1", "tests_2", "loader"};
      test_testMethodPrefix__loadTestsFromTestCase$134 = Py.newCode(1, var2, var1, "test_testMethodPrefix__loadTestsFromTestCase", 1007, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$135 = Py.newCode(0, var2, var1, "Foo", 1008, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$136 = Py.newCode(1, var2, var1, "test_1", 1009, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$137 = Py.newCode(1, var2, var1, "test_2", 1010, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$138 = Py.newCode(1, var2, var1, "foo_bar", 1011, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests_1", "tests_2", "loader"};
      test_testMethodPrefix__loadTestsFromModule$139 = Py.newCode(1, var2, var1, "test_testMethodPrefix__loadTestsFromModule", 1028, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$140 = Py.newCode(0, var2, var1, "Foo", 1030, false, false, self, 140, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$141 = Py.newCode(1, var2, var1, "test_1", 1031, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$142 = Py.newCode(1, var2, var1, "test_2", 1032, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$143 = Py.newCode(1, var2, var1, "foo_bar", 1033, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests_1", "tests_2", "loader"};
      test_testMethodPrefix__loadTestsFromName$144 = Py.newCode(1, var2, var1, "test_testMethodPrefix__loadTestsFromName", 1051, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$145 = Py.newCode(0, var2, var1, "Foo", 1053, false, false, self, 145, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$146 = Py.newCode(1, var2, var1, "test_1", 1054, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$147 = Py.newCode(1, var2, var1, "test_2", 1055, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$148 = Py.newCode(1, var2, var1, "foo_bar", 1056, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests_1", "tests_2", "loader"};
      test_testMethodPrefix__loadTestsFromNames$149 = Py.newCode(1, var2, var1, "test_testMethodPrefix__loadTestsFromNames", 1074, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$150 = Py.newCode(0, var2, var1, "Foo", 1076, false, false, self, 150, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$151 = Py.newCode(1, var2, var1, "test_1", 1077, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$152 = Py.newCode(1, var2, var1, "test_2", 1078, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$153 = Py.newCode(1, var2, var1, "foo_bar", 1079, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_testMethodPrefix__default_value$154 = Py.newCode(1, var2, var1, "test_testMethodPrefix__default_value", 1094, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_cmp", "Foo", "loader", "tests"};
      test_sortTestMethodsUsing__loadTestsFromTestCase$155 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__loadTestsFromTestCase", 1106, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      reversed_cmp$156 = Py.newCode(2, var2, var1, "reversed_cmp", 1107, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$157 = Py.newCode(0, var2, var1, "Foo", 1110, false, false, self, 157, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$158 = Py.newCode(1, var2, var1, "test_1", 1111, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$159 = Py.newCode(1, var2, var1, "test_2", 1112, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_cmp", "m", "Foo", "loader", "tests"};
      test_sortTestMethodsUsing__loadTestsFromModule$160 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__loadTestsFromModule", 1122, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      reversed_cmp$161 = Py.newCode(2, var2, var1, "reversed_cmp", 1123, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$162 = Py.newCode(0, var2, var1, "Foo", 1127, false, false, self, 162, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$163 = Py.newCode(1, var2, var1, "test_1", 1128, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$164 = Py.newCode(1, var2, var1, "test_2", 1129, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_cmp", "m", "Foo", "loader", "tests"};
      test_sortTestMethodsUsing__loadTestsFromName$165 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__loadTestsFromName", 1140, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      reversed_cmp$166 = Py.newCode(2, var2, var1, "reversed_cmp", 1141, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$167 = Py.newCode(0, var2, var1, "Foo", 1145, false, false, self, 167, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$168 = Py.newCode(1, var2, var1, "test_1", 1146, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$169 = Py.newCode(1, var2, var1, "test_2", 1147, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_cmp", "m", "Foo", "loader", "tests"};
      test_sortTestMethodsUsing__loadTestsFromNames$170 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__loadTestsFromNames", 1158, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      reversed_cmp$171 = Py.newCode(2, var2, var1, "reversed_cmp", 1159, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$172 = Py.newCode(0, var2, var1, "Foo", 1163, false, false, self, 172, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$173 = Py.newCode(1, var2, var1, "test_1", 1164, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$174 = Py.newCode(1, var2, var1, "test_2", 1165, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_cmp", "Foo", "loader", "test_names"};
      test_sortTestMethodsUsing__getTestCaseNames$175 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__getTestCaseNames", 1178, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      reversed_cmp$176 = Py.newCode(2, var2, var1, "reversed_cmp", 1179, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$177 = Py.newCode(0, var2, var1, "Foo", 1182, false, false, self, 177, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$178 = Py.newCode(1, var2, var1, "test_1", 1183, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$179 = Py.newCode(1, var2, var1, "test_2", 1184, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_sortTestMethodsUsing__default_value$180 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__default_value", 1193, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "loader", "test_names"};
      test_sortTestMethodsUsing__None$181 = Py.newCode(1, var2, var1, "test_sortTestMethodsUsing__None", 1201, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$182 = Py.newCode(0, var2, var1, "Foo", 1202, false, false, self, 182, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$183 = Py.newCode(1, var2, var1, "test_1", 1203, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$184 = Py.newCode(1, var2, var1, "test_2", 1204, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "tests", "loader"};
      test_suiteClass__loadTestsFromTestCase$185 = Py.newCode(1, var2, var1, "test_suiteClass__loadTestsFromTestCase", 1219, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$186 = Py.newCode(0, var2, var1, "Foo", 1220, false, false, self, 186, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$187 = Py.newCode(1, var2, var1, "test_1", 1221, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$188 = Py.newCode(1, var2, var1, "test_2", 1222, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$189 = Py.newCode(1, var2, var1, "foo_bar", 1223, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests", "loader"};
      test_suiteClass__loadTestsFromModule$190 = Py.newCode(1, var2, var1, "test_suiteClass__loadTestsFromModule", 1233, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$191 = Py.newCode(0, var2, var1, "Foo", 1235, false, false, self, 191, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$192 = Py.newCode(1, var2, var1, "test_1", 1236, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$193 = Py.newCode(1, var2, var1, "test_2", 1237, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$194 = Py.newCode(1, var2, var1, "foo_bar", 1238, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests", "loader"};
      test_suiteClass__loadTestsFromName$195 = Py.newCode(1, var2, var1, "test_suiteClass__loadTestsFromName", 1249, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$196 = Py.newCode(0, var2, var1, "Foo", 1251, false, false, self, 196, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$197 = Py.newCode(1, var2, var1, "test_1", 1252, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$198 = Py.newCode(1, var2, var1, "test_2", 1253, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$199 = Py.newCode(1, var2, var1, "foo_bar", 1254, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "Foo", "tests", "loader"};
      test_suiteClass__loadTestsFromNames$200 = Py.newCode(1, var2, var1, "test_suiteClass__loadTestsFromNames", 1265, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$201 = Py.newCode(0, var2, var1, "Foo", 1267, false, false, self, 201, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$202 = Py.newCode(1, var2, var1, "test_1", 1268, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$203 = Py.newCode(1, var2, var1, "test_2", 1269, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      foo_bar$204 = Py.newCode(1, var2, var1, "foo_bar", 1270, false, false, self, 204, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader"};
      test_suiteClass__default_value$205 = Py.newCode(1, var2, var1, "test_suiteClass__default_value", 1280, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_loader$py("unittest/test/test_loader$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_loader$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_TestLoader$1(var2, var3);
         case 2:
            return this.test_loadTestsFromTestCase$2(var2, var3);
         case 3:
            return this.Foo$3(var2, var3);
         case 4:
            return this.test_1$4(var2, var3);
         case 5:
            return this.test_2$5(var2, var3);
         case 6:
            return this.foo_bar$6(var2, var3);
         case 7:
            return this.test_loadTestsFromTestCase__no_matches$7(var2, var3);
         case 8:
            return this.Foo$8(var2, var3);
         case 9:
            return this.foo_bar$9(var2, var3);
         case 10:
            return this.test_loadTestsFromTestCase__TestSuite_subclass$10(var2, var3);
         case 11:
            return this.NotATestCase$11(var2, var3);
         case 12:
            return this.test_loadTestsFromTestCase__default_method_name$12(var2, var3);
         case 13:
            return this.Foo$13(var2, var3);
         case 14:
            return this.runTest$14(var2, var3);
         case 15:
            return this.test_loadTestsFromModule__TestCase_subclass$15(var2, var3);
         case 16:
            return this.MyTestCase$16(var2, var3);
         case 17:
            return this.test$17(var2, var3);
         case 18:
            return this.test_loadTestsFromModule__no_TestCase_instances$18(var2, var3);
         case 19:
            return this.test_loadTestsFromModule__no_TestCase_tests$19(var2, var3);
         case 20:
            return this.MyTestCase$20(var2, var3);
         case 21:
            return this.test_loadTestsFromModule__not_a_module$21(var2, var3);
         case 22:
            return this.MyTestCase$22(var2, var3);
         case 23:
            return this.test$23(var2, var3);
         case 24:
            return this.NotAModule$24(var2, var3);
         case 25:
            return this.test_loadTestsFromModule__load_tests$25(var2, var3);
         case 26:
            return this.MyTestCase$26(var2, var3);
         case 27:
            return this.test$27(var2, var3);
         case 28:
            return this.load_tests$28(var2, var3);
         case 29:
            return this.test_loadTestsFromModule__faulty_load_tests$29(var2, var3);
         case 30:
            return this.load_tests$30(var2, var3);
         case 31:
            return this.test_loadTestsFromName__empty_name$31(var2, var3);
         case 32:
            return this.test_loadTestsFromName__malformed_name$32(var2, var3);
         case 33:
            return this.test_loadTestsFromName__unknown_module_name$33(var2, var3);
         case 34:
            return this.test_loadTestsFromName__unknown_attr_name$34(var2, var3);
         case 35:
            return this.test_loadTestsFromName__relative_unknown_name$35(var2, var3);
         case 36:
            return this.test_loadTestsFromName__relative_empty_name$36(var2, var3);
         case 37:
            return this.test_loadTestsFromName__relative_malformed_name$37(var2, var3);
         case 38:
            return this.test_loadTestsFromName__relative_not_a_module$38(var2, var3);
         case 39:
            return this.MyTestCase$39(var2, var3);
         case 40:
            return this.test$40(var2, var3);
         case 41:
            return this.NotAModule$41(var2, var3);
         case 42:
            return this.test_loadTestsFromName__relative_bad_object$42(var2, var3);
         case 43:
            return this.test_loadTestsFromName__relative_TestCase_subclass$43(var2, var3);
         case 44:
            return this.MyTestCase$44(var2, var3);
         case 45:
            return this.test$45(var2, var3);
         case 46:
            return this.test_loadTestsFromName__relative_TestSuite$46(var2, var3);
         case 47:
            return this.MyTestCase$47(var2, var3);
         case 48:
            return this.test$48(var2, var3);
         case 49:
            return this.test_loadTestsFromName__relative_testmethod$49(var2, var3);
         case 50:
            return this.MyTestCase$50(var2, var3);
         case 51:
            return this.test$51(var2, var3);
         case 52:
            return this.test_loadTestsFromName__relative_invalid_testmethod$52(var2, var3);
         case 53:
            return this.MyTestCase$53(var2, var3);
         case 54:
            return this.test$54(var2, var3);
         case 55:
            return this.test_loadTestsFromName__callable__TestSuite$55(var2, var3);
         case 56:
            return this.f$56(var2, var3);
         case 57:
            return this.f$57(var2, var3);
         case 58:
            return this.return_TestSuite$58(var2, var3);
         case 59:
            return this.test_loadTestsFromName__callable__TestCase_instance$59(var2, var3);
         case 60:
            return this.f$60(var2, var3);
         case 61:
            return this.return_TestCase$61(var2, var3);
         case 62:
            return this.test_loadTestsFromName__callable__TestCase_instance_ProperSuiteClass$62(var2, var3);
         case 63:
            return this.SubTestSuite$63(var2, var3);
         case 64:
            return this.f$64(var2, var3);
         case 65:
            return this.return_TestCase$65(var2, var3);
         case 66:
            return this.test_loadTestsFromName__relative_testmethod_ProperSuiteClass$66(var2, var3);
         case 67:
            return this.SubTestSuite$67(var2, var3);
         case 68:
            return this.MyTestCase$68(var2, var3);
         case 69:
            return this.test$69(var2, var3);
         case 70:
            return this.test_loadTestsFromName__callable__wrong_type$70(var2, var3);
         case 71:
            return this.return_wrong$71(var2, var3);
         case 72:
            return this.test_loadTestsFromName__module_not_loaded$72(var2, var3);
         case 73:
            return this.test_loadTestsFromNames__empty_name_list$73(var2, var3);
         case 74:
            return this.test_loadTestsFromNames__relative_empty_name_list$74(var2, var3);
         case 75:
            return this.test_loadTestsFromNames__empty_name$75(var2, var3);
         case 76:
            return this.test_loadTestsFromNames__malformed_name$76(var2, var3);
         case 77:
            return this.test_loadTestsFromNames__unknown_module_name$77(var2, var3);
         case 78:
            return this.test_loadTestsFromNames__unknown_attr_name$78(var2, var3);
         case 79:
            return this.test_loadTestsFromNames__unknown_name_relative_1$79(var2, var3);
         case 80:
            return this.test_loadTestsFromNames__unknown_name_relative_2$80(var2, var3);
         case 81:
            return this.test_loadTestsFromNames__relative_empty_name$81(var2, var3);
         case 82:
            return this.test_loadTestsFromNames__relative_malformed_name$82(var2, var3);
         case 83:
            return this.test_loadTestsFromNames__relative_not_a_module$83(var2, var3);
         case 84:
            return this.MyTestCase$84(var2, var3);
         case 85:
            return this.test$85(var2, var3);
         case 86:
            return this.NotAModule$86(var2, var3);
         case 87:
            return this.test_loadTestsFromNames__relative_bad_object$87(var2, var3);
         case 88:
            return this.test_loadTestsFromNames__relative_TestCase_subclass$88(var2, var3);
         case 89:
            return this.MyTestCase$89(var2, var3);
         case 90:
            return this.test$90(var2, var3);
         case 91:
            return this.test_loadTestsFromNames__relative_TestSuite$91(var2, var3);
         case 92:
            return this.MyTestCase$92(var2, var3);
         case 93:
            return this.test$93(var2, var3);
         case 94:
            return this.test_loadTestsFromNames__relative_testmethod$94(var2, var3);
         case 95:
            return this.MyTestCase$95(var2, var3);
         case 96:
            return this.test$96(var2, var3);
         case 97:
            return this.test_loadTestsFromNames__relative_invalid_testmethod$97(var2, var3);
         case 98:
            return this.MyTestCase$98(var2, var3);
         case 99:
            return this.test$99(var2, var3);
         case 100:
            return this.test_loadTestsFromNames__callable__TestSuite$100(var2, var3);
         case 101:
            return this.f$101(var2, var3);
         case 102:
            return this.f$102(var2, var3);
         case 103:
            return this.return_TestSuite$103(var2, var3);
         case 104:
            return this.test_loadTestsFromNames__callable__TestCase_instance$104(var2, var3);
         case 105:
            return this.f$105(var2, var3);
         case 106:
            return this.return_TestCase$106(var2, var3);
         case 107:
            return this.test_loadTestsFromNames__callable__call_staticmethod$107(var2, var3);
         case 108:
            return this.Test1$108(var2, var3);
         case 109:
            return this.test$109(var2, var3);
         case 110:
            return this.Foo$110(var2, var3);
         case 111:
            return this.foo$111(var2, var3);
         case 112:
            return this.test_loadTestsFromNames__callable__wrong_type$112(var2, var3);
         case 113:
            return this.return_wrong$113(var2, var3);
         case 114:
            return this.test_loadTestsFromNames__module_not_loaded$114(var2, var3);
         case 115:
            return this.test_getTestCaseNames$115(var2, var3);
         case 116:
            return this.Test$116(var2, var3);
         case 117:
            return this.test_1$117(var2, var3);
         case 118:
            return this.test_2$118(var2, var3);
         case 119:
            return this.foobar$119(var2, var3);
         case 120:
            return this.test_getTestCaseNames__no_tests$120(var2, var3);
         case 121:
            return this.Test$121(var2, var3);
         case 122:
            return this.foobar$122(var2, var3);
         case 123:
            return this.test_getTestCaseNames__not_a_TestCase$123(var2, var3);
         case 124:
            return this.BadCase$124(var2, var3);
         case 125:
            return this.test_foo$125(var2, var3);
         case 126:
            return this.test_getTestCaseNames__inheritance$126(var2, var3);
         case 127:
            return this.TestP$127(var2, var3);
         case 128:
            return this.test_1$128(var2, var3);
         case 129:
            return this.test_2$129(var2, var3);
         case 130:
            return this.foobar$130(var2, var3);
         case 131:
            return this.TestC$131(var2, var3);
         case 132:
            return this.test_1$132(var2, var3);
         case 133:
            return this.test_3$133(var2, var3);
         case 134:
            return this.test_testMethodPrefix__loadTestsFromTestCase$134(var2, var3);
         case 135:
            return this.Foo$135(var2, var3);
         case 136:
            return this.test_1$136(var2, var3);
         case 137:
            return this.test_2$137(var2, var3);
         case 138:
            return this.foo_bar$138(var2, var3);
         case 139:
            return this.test_testMethodPrefix__loadTestsFromModule$139(var2, var3);
         case 140:
            return this.Foo$140(var2, var3);
         case 141:
            return this.test_1$141(var2, var3);
         case 142:
            return this.test_2$142(var2, var3);
         case 143:
            return this.foo_bar$143(var2, var3);
         case 144:
            return this.test_testMethodPrefix__loadTestsFromName$144(var2, var3);
         case 145:
            return this.Foo$145(var2, var3);
         case 146:
            return this.test_1$146(var2, var3);
         case 147:
            return this.test_2$147(var2, var3);
         case 148:
            return this.foo_bar$148(var2, var3);
         case 149:
            return this.test_testMethodPrefix__loadTestsFromNames$149(var2, var3);
         case 150:
            return this.Foo$150(var2, var3);
         case 151:
            return this.test_1$151(var2, var3);
         case 152:
            return this.test_2$152(var2, var3);
         case 153:
            return this.foo_bar$153(var2, var3);
         case 154:
            return this.test_testMethodPrefix__default_value$154(var2, var3);
         case 155:
            return this.test_sortTestMethodsUsing__loadTestsFromTestCase$155(var2, var3);
         case 156:
            return this.reversed_cmp$156(var2, var3);
         case 157:
            return this.Foo$157(var2, var3);
         case 158:
            return this.test_1$158(var2, var3);
         case 159:
            return this.test_2$159(var2, var3);
         case 160:
            return this.test_sortTestMethodsUsing__loadTestsFromModule$160(var2, var3);
         case 161:
            return this.reversed_cmp$161(var2, var3);
         case 162:
            return this.Foo$162(var2, var3);
         case 163:
            return this.test_1$163(var2, var3);
         case 164:
            return this.test_2$164(var2, var3);
         case 165:
            return this.test_sortTestMethodsUsing__loadTestsFromName$165(var2, var3);
         case 166:
            return this.reversed_cmp$166(var2, var3);
         case 167:
            return this.Foo$167(var2, var3);
         case 168:
            return this.test_1$168(var2, var3);
         case 169:
            return this.test_2$169(var2, var3);
         case 170:
            return this.test_sortTestMethodsUsing__loadTestsFromNames$170(var2, var3);
         case 171:
            return this.reversed_cmp$171(var2, var3);
         case 172:
            return this.Foo$172(var2, var3);
         case 173:
            return this.test_1$173(var2, var3);
         case 174:
            return this.test_2$174(var2, var3);
         case 175:
            return this.test_sortTestMethodsUsing__getTestCaseNames$175(var2, var3);
         case 176:
            return this.reversed_cmp$176(var2, var3);
         case 177:
            return this.Foo$177(var2, var3);
         case 178:
            return this.test_1$178(var2, var3);
         case 179:
            return this.test_2$179(var2, var3);
         case 180:
            return this.test_sortTestMethodsUsing__default_value$180(var2, var3);
         case 181:
            return this.test_sortTestMethodsUsing__None$181(var2, var3);
         case 182:
            return this.Foo$182(var2, var3);
         case 183:
            return this.test_1$183(var2, var3);
         case 184:
            return this.test_2$184(var2, var3);
         case 185:
            return this.test_suiteClass__loadTestsFromTestCase$185(var2, var3);
         case 186:
            return this.Foo$186(var2, var3);
         case 187:
            return this.test_1$187(var2, var3);
         case 188:
            return this.test_2$188(var2, var3);
         case 189:
            return this.foo_bar$189(var2, var3);
         case 190:
            return this.test_suiteClass__loadTestsFromModule$190(var2, var3);
         case 191:
            return this.Foo$191(var2, var3);
         case 192:
            return this.test_1$192(var2, var3);
         case 193:
            return this.test_2$193(var2, var3);
         case 194:
            return this.foo_bar$194(var2, var3);
         case 195:
            return this.test_suiteClass__loadTestsFromName$195(var2, var3);
         case 196:
            return this.Foo$196(var2, var3);
         case 197:
            return this.test_1$197(var2, var3);
         case 198:
            return this.test_2$198(var2, var3);
         case 199:
            return this.foo_bar$199(var2, var3);
         case 200:
            return this.test_suiteClass__loadTestsFromNames$200(var2, var3);
         case 201:
            return this.Foo$201(var2, var3);
         case 202:
            return this.test_1$202(var2, var3);
         case 203:
            return this.test_2$203(var2, var3);
         case 204:
            return this.foo_bar$204(var2, var3);
         case 205:
            return this.test_suiteClass__default_value$205(var2, var3);
         default:
            return null;
      }
   }
}
