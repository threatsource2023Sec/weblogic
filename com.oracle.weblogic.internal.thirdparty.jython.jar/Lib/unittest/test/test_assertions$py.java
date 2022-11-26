package unittest.test;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/test/test_assertions.py")
public class test_assertions$py extends PyFunctionTable implements PyRunnable {
   static test_assertions$py self;
   static final PyCode f$0;
   static final PyCode Test_Assertions$1;
   static final PyCode test_AlmostEqual$2;
   static final PyCode test_AmostEqualWithDelta$3;
   static final PyCode test_assertRaises$4;
   static final PyCode _raise$5;
   static final PyCode f$6;
   static final PyCode testAssertNotRegexpMatches$7;
   static final PyCode TestLongMessage$8;
   static final PyCode setUp$9;
   static final PyCode TestableTestFalse$10;
   static final PyCode testTest$11;
   static final PyCode TestableTestTrue$12;
   static final PyCode testTest$13;
   static final PyCode testDefault$14;
   static final PyCode test_formatMsg$15;
   static final PyCode test_formatMessage_unicode_error$16;
   static final PyCode f$17;
   static final PyCode assertMessages$18;
   static final PyCode getMethod$19;
   static final PyCode testAssertTrue$20;
   static final PyCode testAssertFalse$21;
   static final PyCode testNotEqual$22;
   static final PyCode testAlmostEqual$23;
   static final PyCode testNotAlmostEqual$24;
   static final PyCode test_baseAssertEqual$25;
   static final PyCode testAssertSequenceEqual$26;
   static final PyCode testAssertSetEqual$27;
   static final PyCode testAssertIn$28;
   static final PyCode testAssertNotIn$29;
   static final PyCode testAssertDictEqual$30;
   static final PyCode testAssertDictContainsSubset$31;
   static final PyCode testAssertMultiLineEqual$32;
   static final PyCode testAssertLess$33;
   static final PyCode testAssertLessEqual$34;
   static final PyCode testAssertGreater$35;
   static final PyCode testAssertGreaterEqual$36;
   static final PyCode testAssertIsNone$37;
   static final PyCode testAssertIsNotNone$38;
   static final PyCode testAssertIs$39;
   static final PyCode testAssertIsNot$40;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("datetime", var1, -1);
      var1.setlocal("datetime", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      PyObject[] var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test_Assertions", var5, Test_Assertions$1);
      var1.setlocal("Test_Assertions", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(105);
      var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestLongMessage", var5, TestLongMessage$8);
      var1.setlocal("TestLongMessage", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(285);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(286);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_Assertions$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(7);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_AlmostEqual$2, (PyObject)null);
      var1.setlocal("test_AlmostEqual", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_AmostEqualWithDelta$3, (PyObject)null);
      var1.setlocal("test_AmostEqualWithDelta", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_assertRaises$4, (PyObject)null);
      var1.setlocal("test_assertRaises", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertNotRegexpMatches$7, (PyObject)null);
      var1.setlocal("testAssertNotRegexpMatches", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_AlmostEqual$2(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      var1.getlocal(0).__getattr__("assertAlmostEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.00000001), (PyObject)Py.newFloat(1.0));
      var1.setline(9);
      var1.getlocal(0).__getattr__("assertNotAlmostEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0000001), (PyObject)Py.newFloat(1.0));
      var1.setline(10);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertAlmostEqual"), Py.newFloat(1.0000001), Py.newFloat(1.0));
      var1.setline(12);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotAlmostEqual"), Py.newFloat(1.00000001), Py.newFloat(1.0));
      var1.setline(15);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertAlmostEqual");
      PyObject[] var3 = new PyObject[]{Py.newFloat(1.1), Py.newFloat(1.0), Py.newInteger(0)};
      String[] var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(16);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertAlmostEqual"), Py.newFloat(1.1), Py.newFloat(1.0), Py.newInteger(1)};
      var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(19);
      var10000 = var1.getlocal(0).__getattr__("assertAlmostEqual");
      var3 = new PyObject[]{Py.newInteger(0), Py.newFloat(0.1)._add(Py.newImaginary(0.1)), Py.newInteger(0)};
      var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(20);
      var10000 = var1.getlocal(0).__getattr__("assertNotAlmostEqual");
      var3 = new PyObject[]{Py.newInteger(0), Py.newFloat(0.1)._add(Py.newImaginary(0.1)), Py.newInteger(1)};
      var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(21);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertAlmostEqual"), Py.newInteger(0), Py.newFloat(0.1)._add(Py.newImaginary(0.1)), Py.newInteger(1)};
      var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(23);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotAlmostEqual"), Py.newInteger(0), Py.newFloat(0.1)._add(Py.newImaginary(0.1)), Py.newInteger(0)};
      var4 = new String[]{"places"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertAlmostEqual").__call__(var2, var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf")), var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf")));
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotAlmostEqual"), var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf")), var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_AmostEqualWithDelta$3(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertAlmostEqual");
      PyObject[] var3 = new PyObject[]{Py.newFloat(1.1), Py.newFloat(1.0), Py.newFloat(0.5)};
      String[] var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(32);
      var10000 = var1.getlocal(0).__getattr__("assertAlmostEqual");
      var3 = new PyObject[]{Py.newFloat(1.0), Py.newFloat(1.1), Py.newFloat(0.5)};
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(33);
      var10000 = var1.getlocal(0).__getattr__("assertNotAlmostEqual");
      var3 = new PyObject[]{Py.newFloat(1.1), Py.newFloat(1.0), Py.newFloat(0.05)};
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(34);
      var10000 = var1.getlocal(0).__getattr__("assertNotAlmostEqual");
      var3 = new PyObject[]{Py.newFloat(1.0), Py.newFloat(1.1), Py.newFloat(0.05)};
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(36);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertAlmostEqual"), Py.newFloat(1.1), Py.newFloat(1.0), Py.newFloat(0.05)};
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(38);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotAlmostEqual"), Py.newFloat(1.1), Py.newFloat(1.0), Py.newFloat(0.5)};
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(41);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("assertAlmostEqual"), Py.newFloat(1.1), Py.newFloat(1.0), Py.newInteger(2), Py.newInteger(2)};
      var4 = new String[]{"places", "delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(43);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("assertNotAlmostEqual"), Py.newFloat(1.1), Py.newFloat(1.0), Py.newInteger(2), Py.newInteger(2)};
      var4 = new String[]{"places", "delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(46);
      PyObject var7 = var1.getglobal("datetime").__getattr__("datetime").__getattr__("now").__call__(var2);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(47);
      var10000 = var1.getlocal(1);
      PyObject var10001 = var1.getglobal("datetime").__getattr__("timedelta");
      var3 = new PyObject[]{Py.newInteger(10)};
      var4 = new String[]{"seconds"};
      var10001 = var10001.__call__(var2, var3, var4);
      var3 = null;
      var7 = var10000._add(var10001);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(48);
      var10000 = var1.getlocal(0).__getattr__("assertAlmostEqual");
      var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), null};
      PyObject var10002 = var1.getglobal("datetime").__getattr__("timedelta");
      PyObject[] var6 = new PyObject[]{Py.newInteger(20)};
      String[] var5 = new String[]{"seconds"};
      var10002 = var10002.__call__(var2, var6, var5);
      var4 = null;
      var3[2] = var10002;
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(50);
      var10000 = var1.getlocal(0).__getattr__("assertNotAlmostEqual");
      var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), null};
      var10002 = var1.getglobal("datetime").__getattr__("timedelta");
      var6 = new PyObject[]{Py.newInteger(5)};
      var5 = new String[]{"seconds"};
      var10002 = var10002.__call__(var2, var6, var5);
      var4 = null;
      var3[2] = var10002;
      var4 = new String[]{"delta"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_assertRaises$4(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[4];
      var1.setline(54);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var15 = new PyFunction(var1.f_globals, var3, _raise$5, (PyObject)null);
      var1.setlocal(1, var15);
      var3 = null;
      var1.setline(56);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(1), var1.getglobal("KeyError"));
      var1.setline(57);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(1), var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("key")));

      PyObject var4;
      PyException var16;
      label117: {
         try {
            var1.setline(59);
            PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
            PyObject var10002 = var1.getglobal("KeyError");
            var1.setline(59);
            var3 = Py.EmptyObjects;
            var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var1.f_globals, var3, f$6)));
         } catch (Throwable var14) {
            var16 = Py.setException(var14, var1);
            if (var16.match(var1.getlocal(0).__getattr__("failureException"))) {
               var4 = var16.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(61);
               var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("KeyError not raised"), (PyObject)var1.getlocal(2).__getattr__("args"));
               break label117;
            }

            throw var16;
         }

         var1.setline(63);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertRaises() didn't fail"));
      }

      label110: {
         try {
            var1.setline(65);
            var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(1), var1.getglobal("ValueError"));
         } catch (Throwable var13) {
            var16 = Py.setException(var13, var1);
            if (var16.match(var1.getglobal("ValueError"))) {
               var1.setline(67);
               break label110;
            }

            throw var16;
         }

         var1.setline(69);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertRaises() didn't let exception pass through"));
      }

      ContextManager var17;
      var4 = (var17 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError")))).__enter__(var2);

      try {
         var1.setlocal(3, var4);

         try {
            var1.setline(72);
            throw Py.makeException(var1.getglobal("KeyError"));
         } catch (Throwable var11) {
            PyException var18 = Py.setException(var11, var1);
            if (var18.match(var1.getglobal("Exception"))) {
               PyObject var5 = var18.value;
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(74);
               throw Py.makeException();
            } else {
               throw var18;
            }
         }
      } catch (Throwable var12) {
         if (!var17.__exit__(var2, Py.setException(var12, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.setline(75);
            var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(3).__getattr__("exception"), var1.getlocal(2));
            var4 = (var17 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError")))).__enter__(var2);

            try {
               var1.setline(78);
               throw Py.makeException(var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("key")));
            } catch (Throwable var10) {
               if (!var17.__exit__(var2, Py.setException(var10, var1))) {
                  throw (Throwable)Py.makeException();
               } else {
                  label88: {
                     try {
                        label123: {
                           var4 = (var17 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError")))).__enter__(var2);

                           try {
                              var1.setline(81);
                           } catch (Throwable var8) {
                              if (var17.__exit__(var2, Py.setException(var8, var1))) {
                                 break label123;
                              }

                              throw (Throwable)Py.makeException();
                           }

                           var17.__exit__(var2, (PyException)null);
                        }
                     } catch (Throwable var9) {
                        var16 = Py.setException(var9, var1);
                        if (var16.match(var1.getlocal(0).__getattr__("failureException"))) {
                           var4 = var16.value;
                           var1.setlocal(2, var4);
                           var4 = null;
                           var1.setline(83);
                           var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("KeyError not raised"), (PyObject)var1.getlocal(2).__getattr__("args"));
                           break label88;
                        }

                        throw var16;
                     }

                     var1.setline(85);
                     var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertRaises() didn't fail"));
                  }

                  label74: {
                     try {
                        var4 = (var17 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError")))).__enter__(var2);

                        try {
                           var1.setline(88);
                           throw Py.makeException(var1.getglobal("ValueError"));
                        } catch (Throwable var6) {
                           if (!var17.__exit__(var2, Py.setException(var6, var1))) {
                              throw (Throwable)Py.makeException();
                           }
                        }
                     } catch (Throwable var7) {
                        var16 = Py.setException(var7, var1);
                        if (!var16.match(var1.getglobal("ValueError"))) {
                           throw var16;
                        }

                        var1.setline(90);
                        break label74;
                     }

                     var1.setline(92);
                     var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertRaises() didn't let exception pass through"));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject _raise$5(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      throw Py.makeException(var1.getlocal(0));
   }

   public PyObject f$6(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertNotRegexpMatches$7(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      var1.getlocal(0).__getattr__("assertNotRegexpMatches").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Ala ma kota"), (PyObject)PyString.fromInterned("r+"));

      label19: {
         try {
            var1.setline(97);
            var1.getlocal(0).__getattr__("assertNotRegexpMatches").__call__((ThreadState)var2, PyString.fromInterned("Ala ma kota"), (PyObject)PyString.fromInterned("k.t"), (PyObject)PyString.fromInterned("Message"));
         } catch (Throwable var5) {
            PyException var3 = Py.setException(var5, var1);
            if (var3.match(var1.getlocal(0).__getattr__("failureException"))) {
               PyObject var4 = var3.value;
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(99);
               var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'kot'"), (PyObject)var1.getlocal(1).__getattr__("args").__getitem__(Py.newInteger(0)));
               var1.setline(100);
               var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Message"), (PyObject)var1.getlocal(1).__getattr__("args").__getitem__(Py.newInteger(0)));
               break label19;
            }

            throw var3;
         }

         var1.setline(102);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertNotRegexpMatches should have failed."));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestLongMessage$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Test that the individual asserts honour longMessage.\n    This actually tests all the message behaviour for\n    asserts that use longMessage."));
      var1.setline(108);
      PyString.fromInterned("Test that the individual asserts honour longMessage.\n    This actually tests all the message behaviour for\n    asserts that use longMessage.");
      var1.setline(110);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$9, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testDefault$14, (PyObject)null);
      var1.setlocal("testDefault", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatMsg$15, (PyObject)null);
      var1.setlocal("test_formatMsg", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatMessage_unicode_error$16, (PyObject)null);
      var1.setlocal("test_formatMessage_unicode_error", var4);
      var3 = null;
      var1.setline(146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, assertMessages$18, (PyObject)null);
      var1.setlocal("assertMessages", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertTrue$20, (PyObject)null);
      var1.setlocal("testAssertTrue", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertFalse$21, (PyObject)null);
      var1.setlocal("testAssertFalse", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testNotEqual$22, (PyObject)null);
      var1.setlocal("testNotEqual", var4);
      var3 = null;
      var1.setline(181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAlmostEqual$23, (PyObject)null);
      var1.setlocal("testAlmostEqual", var4);
      var3 = null;
      var1.setline(186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testNotAlmostEqual$24, (PyObject)null);
      var1.setlocal("testNotAlmostEqual", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_baseAssertEqual$25, (PyObject)null);
      var1.setlocal("test_baseAssertEqual", var4);
      var3 = null;
      var1.setline(195);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertSequenceEqual$26, (PyObject)null);
      var1.setlocal("testAssertSequenceEqual", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertSetEqual$27, (PyObject)null);
      var1.setlocal("testAssertSetEqual", var4);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertIn$28, (PyObject)null);
      var1.setlocal("testAssertIn", var4);
      var3 = null;
      var1.setline(213);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertNotIn$29, (PyObject)null);
      var1.setlocal("testAssertNotIn", var4);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertDictEqual$30, (PyObject)null);
      var1.setlocal("testAssertDictEqual", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertDictContainsSubset$31, (PyObject)null);
      var1.setlocal("testAssertDictContainsSubset", var4);
      var3 = null;
      var1.setline(231);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertMultiLineEqual$32, (PyObject)null);
      var1.setlocal("testAssertMultiLineEqual", var4);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertLess$33, (PyObject)null);
      var1.setlocal("testAssertLess", var4);
      var3 = null;
      var1.setline(242);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertLessEqual$34, (PyObject)null);
      var1.setlocal("testAssertLessEqual", var4);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertGreater$35, (PyObject)null);
      var1.setlocal("testAssertGreater", var4);
      var3 = null;
      var1.setline(254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertGreaterEqual$36, (PyObject)null);
      var1.setlocal("testAssertGreaterEqual", var4);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertIsNone$37, (PyObject)null);
      var1.setlocal("testAssertIsNone", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertIsNotNone$38, (PyObject)null);
      var1.setlocal("testAssertIsNotNone", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertIs$39, (PyObject)null);
      var1.setlocal("testAssertIs", var4);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testAssertIsNot$40, (PyObject)null);
      var1.setlocal("testAssertIsNot", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$9(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(111);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyCode var10002 = TestableTestFalse$10;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyObject var6 = Py.makeClass("TestableTestFalse", var3, var10002, var4);
      var1.setlocal(1, var6);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(118);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var10002 = TestableTestTrue$12;
      var4 = new PyObject[]{var1.getclosure(0)};
      var6 = Py.makeClass("TestableTestTrue", var3, var10002, var4);
      var1.setlocal(2, var6);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(125);
      PyObject var5 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testTest"));
      var1.getderef(0).__setattr__("testableTrue", var5);
      var3 = null;
      var1.setline(126);
      var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testTest"));
      var1.getderef(0).__setattr__("testableFalse", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTestFalse$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(112);
      PyObject var3 = var1.getname("False");
      var1.setlocal("longMessage", var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getderef(0).__getattr__("failureException");
      var1.setlocal("failureException", var3);
      var3 = null;
      var1.setline(115);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, testTest$11, (PyObject)null);
      var1.setlocal("testTest", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testTest$11(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTestTrue$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(119);
      PyObject var3 = var1.getname("True");
      var1.setlocal("longMessage", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getderef(0).__getattr__("failureException");
      var1.setlocal("failureException", var3);
      var3 = null;
      var1.setline(122);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, testTest$13, (PyObject)null);
      var1.setlocal("testTest", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testTest$13(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testDefault$14(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("unittest").__getattr__("TestCase").__getattr__("longMessage"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatMsg$15(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("testableFalse").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)PyString.fromInterned("foo")), (PyObject)PyString.fromInterned("foo"));
      var1.setline(133);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("testableFalse").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar")), (PyObject)PyString.fromInterned("foo"));
      var1.setline(135);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("testableTrue").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)PyString.fromInterned("foo")), (PyObject)PyString.fromInterned("foo"));
      var1.setline(136);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("testableTrue").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar")), (PyObject)PyString.fromInterned("bar : foo"));
      var1.setline(139);
      var1.getlocal(0).__getattr__("testableTrue").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)var1.getglobal("object").__call__(var2), (PyObject)PyString.fromInterned("foo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatMessage_unicode_error$16(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      var1.setline(142);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, f$17, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(255)).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(144);
      var1.getlocal(0).__getattr__("testableTrue").__getattr__("_formatMessage").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyUnicode.fromInterned("�"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$17(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(142);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(142);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(142);
         var1.setline(142);
         var6 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject assertMessages$18(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(0, 1);
      var1.f_exits = new PyObject[1];
      var1.setline(147);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = getMethod$19;
      var3 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      PyFunction var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(155);
      PyObject var10 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3)).__iter__();

      while(true) {
         ContextManager var15;
         while(true) {
            var1.setline(155);
            PyObject var4 = var10.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(156);
            PyObject var11 = var1.getlocal(4).__call__(var2, var1.getlocal(5));
            var1.setlocal(7, var11);
            var5 = null;
            var1.setline(157);
            PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var12);
            var5 = null;
            var1.setline(158);
            var11 = var1.getlocal(5)._mod(Py.newInteger(2));
            var1.setlocal(9, var11);
            var5 = null;
            var1.setline(159);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(160);
               var12 = new PyDictionary(new PyObject[]{PyString.fromInterned("msg"), PyString.fromInterned("oops")});
               var1.setlocal(8, var12);
               var5 = null;
            }

            PyObject var10000 = var1.getderef(1).__getattr__("assertRaisesRegexp");
            var5 = new PyObject[]{var1.getderef(1).__getattr__("failureException"), var1.getlocal(6)};
            String[] var13 = new String[]{"expected_regexp"};
            var10000 = var10000.__call__(var2, var5, var13);
            var5 = null;
            var6 = (var15 = ContextGuard.getManager(var10000)).__enter__(var2);

            try {
               var1.setline(164);
               var10000 = var1.getlocal(7);
               PyObject[] var14 = Py.EmptyObjects;
               String[] var7 = new String[0];
               var10000._callextra(var14, var7, var1.getlocal(2), var1.getlocal(8));
               var6 = null;
               break;
            } catch (Throwable var8) {
               if (!var15.__exit__(var2, Py.setException(var8, var1))) {
                  throw (Throwable)Py.makeException();
               }
            }
         }

         var15.__exit__(var2, (PyException)null);
      }
   }

   public PyObject getMethod$19(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(Py.newInteger(2));
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(149);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(150);
         var3 = var1.getderef(0).__getattr__("testableFalse");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(152);
         var3 = var1.getderef(0).__getattr__("testableTrue");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(153);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getderef(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertTrue$20(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertTrue"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("False")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^False is not true$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^False is not true$"), PyString.fromInterned("^False is not true : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertFalse$21(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertFalse"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("True")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^True is not false$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^True is not false$"), PyString.fromInterned("^True is not false : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testNotEqual$22(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertNotEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(1)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 == 1$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 == 1$"), PyString.fromInterned("^1 == 1 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAlmostEqual$23(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertAlmostEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 != 2 within 7 places$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 != 2 within 7 places$"), PyString.fromInterned("^1 != 2 within 7 places : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testNotAlmostEqual$24(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertNotAlmostEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(1)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 == 1 within 7 places$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 == 1 within 7 places$"), PyString.fromInterned("^1 == 1 within 7 places : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_baseAssertEqual$25(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("_baseAssertEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 != 2$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 != 2$"), PyString.fromInterned("^1 != 2 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertSequenceEqual$26(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertSequenceEqual"), (PyObject)(new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getglobal("None")})})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("\\+ \\[None\\]$"), PyString.fromInterned("^oops$"), PyString.fromInterned("\\+ \\[None\\]$"), PyString.fromInterned("\\+ \\[None\\] : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertSetEqual$27(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertSetEqual"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("set").__call__(var2), var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None")})))})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("None$"), PyString.fromInterned("^oops$"), PyString.fromInterned("None$"), PyString.fromInterned("None : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIn$28(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertIn"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), new PyList(Py.EmptyObjects)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^None not found in \\[\\]$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^None not found in \\[\\]$"), PyString.fromInterned("^None not found in \\[\\] : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertNotIn$29(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertNotIn"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), new PyList(new PyObject[]{var1.getglobal("None")})})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^None unexpectedly found in \\[None\\]$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^None unexpectedly found in \\[None\\]$"), PyString.fromInterned("^None unexpectedly found in \\[None\\] : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertDictEqual$30(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertDictEqual"), (PyObject)(new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyDictionary(new PyObject[]{PyString.fromInterned("key"), PyString.fromInterned("value")})})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("\\+ \\{'key': 'value'\\}$"), PyString.fromInterned("^oops$"), PyString.fromInterned("\\+ \\{'key': 'value'\\}$"), PyString.fromInterned("\\+ \\{'key': 'value'\\} : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertDictContainsSubset$31(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertDictContainsSubset"), (PyObject)(new PyTuple(new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("key"), PyString.fromInterned("value")}), new PyDictionary(Py.EmptyObjects)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^Missing: 'key'$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^Missing: 'key'$"), PyString.fromInterned("^Missing: 'key' : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertMultiLineEqual$32(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertMultiLineEqual"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("foo")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("\\+ foo$"), PyString.fromInterned("^oops$"), PyString.fromInterned("\\+ foo$"), PyString.fromInterned("\\+ foo : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertLess$33(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertLess"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(1)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^2 not less than 1$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^2 not less than 1$"), PyString.fromInterned("^2 not less than 1 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertLessEqual$34(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertLessEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(1)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^2 not less than or equal to 1$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^2 not less than or equal to 1$"), PyString.fromInterned("^2 not less than or equal to 1 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertGreater$35(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertGreater"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 not greater than 2$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 not greater than 2$"), PyString.fromInterned("^1 not greater than 2 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertGreaterEqual$36(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertGreaterEqual"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^1 not greater than or equal to 2$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^1 not greater than or equal to 2$"), PyString.fromInterned("^1 not greater than or equal to 2 : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsNone$37(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertIsNone"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("not None")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^'not None' is not None$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^'not None' is not None$"), PyString.fromInterned("^'not None' is not None : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsNotNone$38(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertIsNotNone"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^unexpectedly None$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^unexpectedly None$"), PyString.fromInterned("^unexpectedly None : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIs$39(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertIs"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("foo")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^None is not 'foo'$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^None is not 'foo'$"), PyString.fromInterned("^None is not 'foo' : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsNot$40(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      var1.getlocal(0).__getattr__("assertMessages").__call__((ThreadState)var2, PyString.fromInterned("assertIsNot"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("^unexpectedly identical: None$"), PyString.fromInterned("^oops$"), PyString.fromInterned("^unexpectedly identical: None$"), PyString.fromInterned("^unexpectedly identical: None : oops$")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_assertions$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_Assertions$1 = Py.newCode(0, var2, var1, "Test_Assertions", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_AlmostEqual$2 = Py.newCode(1, var2, var1, "test_AlmostEqual", 7, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second"};
      test_AmostEqualWithDelta$3 = Py.newCode(1, var2, var1, "test_AmostEqualWithDelta", 30, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_raise", "e", "cm"};
      test_assertRaises$4 = Py.newCode(1, var2, var1, "test_assertRaises", 53, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"e"};
      _raise$5 = Py.newCode(1, var2, var1, "_raise", 54, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$6 = Py.newCode(0, var2, var1, "<lambda>", 59, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "e"};
      testAssertNotRegexpMatches$7 = Py.newCode(1, var2, var1, "testAssertNotRegexpMatches", 94, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestLongMessage$8 = Py.newCode(0, var2, var1, "TestLongMessage", 105, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "TestableTestFalse", "TestableTestTrue"};
      String[] var10001 = var2;
      test_assertions$py var10007 = self;
      var2 = new String[]{"self"};
      setUp$9 = Py.newCode(1, var10001, var1, "setUp", 110, false, false, var10007, 9, var2, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      TestableTestFalse$10 = Py.newCode(0, var10001, var1, "TestableTestFalse", 111, false, false, var10007, 10, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self"};
      testTest$11 = Py.newCode(1, var2, var1, "testTest", 115, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      TestableTestTrue$12 = Py.newCode(0, var10001, var1, "TestableTestTrue", 118, false, false, var10007, 12, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self"};
      testTest$13 = Py.newCode(1, var2, var1, "testTest", 122, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testDefault$14 = Py.newCode(1, var2, var1, "testDefault", 128, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_formatMsg$15 = Py.newCode(1, var2, var1, "test_formatMsg", 131, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "one", "_(142_22)"};
      test_formatMessage_unicode_error$16 = Py.newCode(1, var2, var1, "test_formatMessage_unicode_error", 141, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "i"};
      f$17 = Py.newCode(1, var2, var1, "<genexpr>", 142, false, false, self, 17, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "methodName", "args", "errors", "getMethod", "i", "expected_regexp", "testMethod", "kwargs", "withMsg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"methodName", "self"};
      assertMessages$18 = Py.newCode(4, var10001, var1, "assertMessages", 146, false, false, var10007, 18, var2, (String[])null, 0, 4097);
      var2 = new String[]{"i", "useTestableFalse", "test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "methodName"};
      getMethod$19 = Py.newCode(1, var10001, var1, "getMethod", 147, false, false, var10007, 19, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      testAssertTrue$20 = Py.newCode(1, var2, var1, "testAssertTrue", 166, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertFalse$21 = Py.newCode(1, var2, var1, "testAssertFalse", 171, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testNotEqual$22 = Py.newCode(1, var2, var1, "testNotEqual", 176, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAlmostEqual$23 = Py.newCode(1, var2, var1, "testAlmostEqual", 181, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testNotAlmostEqual$24 = Py.newCode(1, var2, var1, "testNotAlmostEqual", 186, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_baseAssertEqual$25 = Py.newCode(1, var2, var1, "test_baseAssertEqual", 191, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertSequenceEqual$26 = Py.newCode(1, var2, var1, "testAssertSequenceEqual", 195, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertSetEqual$27 = Py.newCode(1, var2, var1, "testAssertSetEqual", 202, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIn$28 = Py.newCode(1, var2, var1, "testAssertIn", 207, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertNotIn$29 = Py.newCode(1, var2, var1, "testAssertNotIn", 213, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertDictEqual$30 = Py.newCode(1, var2, var1, "testAssertDictEqual", 219, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertDictContainsSubset$31 = Py.newCode(1, var2, var1, "testAssertDictContainsSubset", 225, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertMultiLineEqual$32 = Py.newCode(1, var2, var1, "testAssertMultiLineEqual", 231, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertLess$33 = Py.newCode(1, var2, var1, "testAssertLess", 237, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertLessEqual$34 = Py.newCode(1, var2, var1, "testAssertLessEqual", 242, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertGreater$35 = Py.newCode(1, var2, var1, "testAssertGreater", 248, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertGreaterEqual$36 = Py.newCode(1, var2, var1, "testAssertGreaterEqual", 254, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIsNone$37 = Py.newCode(1, var2, var1, "testAssertIsNone", 260, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIsNotNone$38 = Py.newCode(1, var2, var1, "testAssertIsNotNone", 266, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIs$39 = Py.newCode(1, var2, var1, "testAssertIs", 272, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIsNot$40 = Py.newCode(1, var2, var1, "testAssertIsNot", 278, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_assertions$py("unittest/test/test_assertions$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_assertions$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_Assertions$1(var2, var3);
         case 2:
            return this.test_AlmostEqual$2(var2, var3);
         case 3:
            return this.test_AmostEqualWithDelta$3(var2, var3);
         case 4:
            return this.test_assertRaises$4(var2, var3);
         case 5:
            return this._raise$5(var2, var3);
         case 6:
            return this.f$6(var2, var3);
         case 7:
            return this.testAssertNotRegexpMatches$7(var2, var3);
         case 8:
            return this.TestLongMessage$8(var2, var3);
         case 9:
            return this.setUp$9(var2, var3);
         case 10:
            return this.TestableTestFalse$10(var2, var3);
         case 11:
            return this.testTest$11(var2, var3);
         case 12:
            return this.TestableTestTrue$12(var2, var3);
         case 13:
            return this.testTest$13(var2, var3);
         case 14:
            return this.testDefault$14(var2, var3);
         case 15:
            return this.test_formatMsg$15(var2, var3);
         case 16:
            return this.test_formatMessage_unicode_error$16(var2, var3);
         case 17:
            return this.f$17(var2, var3);
         case 18:
            return this.assertMessages$18(var2, var3);
         case 19:
            return this.getMethod$19(var2, var3);
         case 20:
            return this.testAssertTrue$20(var2, var3);
         case 21:
            return this.testAssertFalse$21(var2, var3);
         case 22:
            return this.testNotEqual$22(var2, var3);
         case 23:
            return this.testAlmostEqual$23(var2, var3);
         case 24:
            return this.testNotAlmostEqual$24(var2, var3);
         case 25:
            return this.test_baseAssertEqual$25(var2, var3);
         case 26:
            return this.testAssertSequenceEqual$26(var2, var3);
         case 27:
            return this.testAssertSetEqual$27(var2, var3);
         case 28:
            return this.testAssertIn$28(var2, var3);
         case 29:
            return this.testAssertNotIn$29(var2, var3);
         case 30:
            return this.testAssertDictEqual$30(var2, var3);
         case 31:
            return this.testAssertDictContainsSubset$31(var2, var3);
         case 32:
            return this.testAssertMultiLineEqual$32(var2, var3);
         case 33:
            return this.testAssertLess$33(var2, var3);
         case 34:
            return this.testAssertLessEqual$34(var2, var3);
         case 35:
            return this.testAssertGreater$35(var2, var3);
         case 36:
            return this.testAssertGreaterEqual$36(var2, var3);
         case 37:
            return this.testAssertIsNone$37(var2, var3);
         case 38:
            return this.testAssertIsNotNone$38(var2, var3);
         case 39:
            return this.testAssertIs$39(var2, var3);
         case 40:
            return this.testAssertIsNot$40(var2, var3);
         default:
            return null;
      }
   }
}
