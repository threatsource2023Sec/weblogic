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
@MTime(1498849384000L)
@Filename("_abcoll.py")
public class _abcoll$py extends PyFunctionTable implements PyRunnable {
   static _abcoll$py self;
   static final PyCode f$0;
   static final PyCode _hasattr$1;
   static final PyCode f$2;
   static final PyCode Hashable$3;
   static final PyCode __hash__$4;
   static final PyCode __subclasshook__$5;
   static final PyCode Iterable$6;
   static final PyCode __iter__$7;
   static final PyCode __subclasshook__$8;
   static final PyCode Iterator$9;
   static final PyCode next$10;
   static final PyCode __iter__$11;
   static final PyCode __subclasshook__$12;
   static final PyCode Sized$13;
   static final PyCode __len__$14;
   static final PyCode __subclasshook__$15;
   static final PyCode Container$16;
   static final PyCode __contains__$17;
   static final PyCode __subclasshook__$18;
   static final PyCode Callable$19;
   static final PyCode __call__$20;
   static final PyCode __subclasshook__$21;
   static final PyCode Set$22;
   static final PyCode __le__$23;
   static final PyCode __lt__$24;
   static final PyCode __gt__$25;
   static final PyCode __ge__$26;
   static final PyCode __eq__$27;
   static final PyCode __ne__$28;
   static final PyCode _from_iterable$29;
   static final PyCode __and__$30;
   static final PyCode f$31;
   static final PyCode isdisjoint$32;
   static final PyCode __or__$33;
   static final PyCode f$34;
   static final PyCode __sub__$35;
   static final PyCode f$36;
   static final PyCode __xor__$37;
   static final PyCode _hash$38;
   static final PyCode MutableSet$39;
   static final PyCode add$40;
   static final PyCode discard$41;
   static final PyCode remove$42;
   static final PyCode pop$43;
   static final PyCode clear$44;
   static final PyCode __ior__$45;
   static final PyCode __iand__$46;
   static final PyCode __ixor__$47;
   static final PyCode __isub__$48;
   static final PyCode Mapping$49;
   static final PyCode __getitem__$50;
   static final PyCode get$51;
   static final PyCode __contains__$52;
   static final PyCode iterkeys$53;
   static final PyCode itervalues$54;
   static final PyCode iteritems$55;
   static final PyCode keys$56;
   static final PyCode items$57;
   static final PyCode values$58;
   static final PyCode __eq__$59;
   static final PyCode __ne__$60;
   static final PyCode MappingView$61;
   static final PyCode __init__$62;
   static final PyCode __len__$63;
   static final PyCode __repr__$64;
   static final PyCode KeysView$65;
   static final PyCode _from_iterable$66;
   static final PyCode __contains__$67;
   static final PyCode __iter__$68;
   static final PyCode ItemsView$69;
   static final PyCode _from_iterable$70;
   static final PyCode __contains__$71;
   static final PyCode __iter__$72;
   static final PyCode ValuesView$73;
   static final PyCode __contains__$74;
   static final PyCode __iter__$75;
   static final PyCode MutableMapping$76;
   static final PyCode __setitem__$77;
   static final PyCode __delitem__$78;
   static final PyCode pop$79;
   static final PyCode popitem$80;
   static final PyCode clear$81;
   static final PyCode update$82;
   static final PyCode setdefault$83;
   static final PyCode Sequence$84;
   static final PyCode __getitem__$85;
   static final PyCode __iter__$86;
   static final PyCode __contains__$87;
   static final PyCode __reversed__$88;
   static final PyCode index$89;
   static final PyCode count$90;
   static final PyCode f$91;
   static final PyCode MutableSequence$92;
   static final PyCode __setitem__$93;
   static final PyCode __delitem__$94;
   static final PyCode insert$95;
   static final PyCode append$96;
   static final PyCode reverse$97;
   static final PyCode extend$98;
   static final PyCode pop$99;
   static final PyCode remove$100;
   static final PyCode __iadd__$101;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Abstract Base Classes (ABCs) for collections, according to PEP 3119.\n\nDON'T USE THIS MODULE DIRECTLY!  The classes here should be imported\nvia collections; they are defined here only to alleviate certain\nbootstrapping issues.  Unit tests are in test_collections.\n"));
      var1.setline(9);
      PyString.fromInterned("Abstract Base Classes (ABCs) for collections, according to PEP 3119.\n\nDON'T USE THIS MODULE DIRECTLY!  The classes here should be imported\nvia collections; they are defined here only to alleviate certain\nbootstrapping issues.  Unit tests are in test_collections.\n");
      var1.setline(11);
      String[] var3 = new String[]{"ABCMeta", "abstractmethod"};
      PyObject[] var5 = imp.importFrom("abc", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ABCMeta", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("abstractmethod", var4);
      var4 = null;
      var1.setline(12);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(14);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Hashable"), PyString.fromInterned("Iterable"), PyString.fromInterned("Iterator"), PyString.fromInterned("Sized"), PyString.fromInterned("Container"), PyString.fromInterned("Callable"), PyString.fromInterned("Set"), PyString.fromInterned("MutableSet"), PyString.fromInterned("Mapping"), PyString.fromInterned("MutableMapping"), PyString.fromInterned("MappingView"), PyString.fromInterned("KeysView"), PyString.fromInterned("ItemsView"), PyString.fromInterned("ValuesView"), PyString.fromInterned("Sequence"), PyString.fromInterned("MutableSequence")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(25);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, _hasattr$1, (PyObject)null);
      var1.setlocal("_hasattr", var8);
      var3 = null;
      var1.setline(33);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Hashable", var5, Hashable$3);
      var1.setlocal("Hashable", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(56);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Iterable", var5, Iterable$6);
      var1.setlocal("Iterable", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(71);
      var1.getname("Iterable").__getattr__("register").__call__(var2, var1.getname("str"));
      var1.setline(74);
      var5 = new PyObject[]{var1.getname("Iterable")};
      var4 = Py.makeClass("Iterator", var5, Iterator$9);
      var1.setlocal("Iterator", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(91);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Sized", var5, Sized$13);
      var1.setlocal("Sized", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(106);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Container", var5, Container$16);
      var1.setlocal("Container", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(121);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Callable", var5, Callable$19);
      var1.setlocal("Callable", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(139);
      var5 = new PyObject[]{var1.getname("Sized"), var1.getname("Iterable"), var1.getname("Container")};
      var4 = Py.makeClass("Set", var5, Set$22);
      var1.setlocal("Set", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(259);
      var1.getname("Set").__getattr__("register").__call__(var2, var1.getname("frozenset"));
      var1.setline(262);
      var5 = new PyObject[]{var1.getname("Set")};
      var4 = Py.makeClass("MutableSet", var5, MutableSet$39);
      var1.setlocal("MutableSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(329);
      var1.getname("MutableSet").__getattr__("register").__call__(var2, var1.getname("set"));
      var1.setline(335);
      var5 = new PyObject[]{var1.getname("Sized"), var1.getname("Iterable"), var1.getname("Container")};
      var4 = Py.makeClass("Mapping", var5, Mapping$49);
      var1.setlocal("Mapping", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(386);
      var5 = new PyObject[]{var1.getname("Sized")};
      var4 = Py.makeClass("MappingView", var5, MappingView$61);
      var1.setlocal("MappingView", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(398);
      var5 = new PyObject[]{var1.getname("MappingView"), var1.getname("Set")};
      var4 = Py.makeClass("KeysView", var5, KeysView$65);
      var1.setlocal("KeysView", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(412);
      var5 = new PyObject[]{var1.getname("MappingView"), var1.getname("Set")};
      var4 = Py.makeClass("ItemsView", var5, ItemsView$69);
      var1.setlocal("ItemsView", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(432);
      var5 = new PyObject[]{var1.getname("MappingView")};
      var4 = Py.makeClass("ValuesView", var5, ValuesView$73);
      var1.setlocal("ValuesView", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(445);
      var5 = new PyObject[]{var1.getname("Mapping")};
      var4 = Py.makeClass("MutableMapping", var5, MutableMapping$76);
      var1.setlocal("MutableMapping", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(512);
      var1.getname("MutableMapping").__getattr__("register").__call__(var2, var1.getname("dict"));
      var1.setline(518);
      var5 = new PyObject[]{var1.getname("Sized"), var1.getname("Iterable"), var1.getname("Container")};
      var4 = Py.makeClass("Sequence", var5, Sequence$84);
      var1.setlocal("Sequence", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(558);
      var1.getname("Sequence").__getattr__("register").__call__(var2, var1.getname("tuple"));
      var1.setline(559);
      var1.getname("Sequence").__getattr__("register").__call__(var2, var1.getname("basestring"));
      var1.setline(560);
      var1.getname("Sequence").__getattr__("register").__call__(var2, var1.getname("buffer"));
      var1.setline(561);
      var1.getname("Sequence").__getattr__("register").__call__(var2, var1.getname("xrange"));
      var1.setline(564);
      var5 = new PyObject[]{var1.getname("Sequence")};
      var4 = Py.makeClass("MutableSequence", var5, MutableSequence$92);
      var1.setlocal("MutableSequence", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(602);
      var1.getname("MutableSequence").__getattr__("register").__call__(var2, var1.getname("list"));
      var1.setline(604);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(605);
         var3 = new String[]{"PyFastSequenceIter"};
         var5 = imp.importFrom("org.python.core", var3, var1, -1);
         var4 = var5[0];
         var1.setlocal("PyFastSequenceIter", var4);
         var4 = null;
         var1.setline(607);
         var3 = new String[]{"List", "Map", "Set"};
         var5 = imp.importFrom("java.util", var3, var1, -1);
         var4 = var5[0];
         var1.setlocal("JList", var4);
         var4 = null;
         var4 = var5[1];
         var1.setlocal("JMap", var4);
         var4 = null;
         var4 = var5[2];
         var1.setlocal("JSet", var4);
         var4 = null;
         var1.setline(609);
         var1.getname("MutableSequence").__getattr__("register").__call__(var2, var1.getname("JList"));
         var1.setline(610);
         var1.getname("MutableMapping").__getattr__("register").__call__(var2, var1.getname("JMap"));
         var1.setline(611);
         var1.getname("MutableSet").__getattr__("register").__call__(var2, var1.getname("JSet"));
         var1.setline(612);
         var1.getname("Iterator").__getattr__("register").__call__(var2, var1.getname("PyFastSequenceIter"));
         var1.setline(614);
         var1.dellocal("PyFastSequenceIter");
         var1.setline(615);
         var1.dellocal("JList");
         var1.setline(616);
         var1.dellocal("JMap");
         var1.setline(617);
         var1.dellocal("JSet");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _hasattr$1(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);

      PyObject var3;
      try {
         var1.setline(27);
         PyObject var10000 = var1.getglobal("any");
         var1.setline(27);
         PyObject var10004 = var1.f_globals;
         PyObject[] var6 = Py.EmptyObjects;
         PyCode var10006 = f$2;
         PyObject[] var7 = new PyObject[]{var1.getclosure(0)};
         PyFunction var8 = new PyFunction(var10004, var6, var10006, (PyObject)null, var7);
         PyObject var10002 = var8.__call__(var2, var1.getlocal(0).__getattr__("__mro__").__iter__());
         Arrays.fill(var6, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(30);
            var3 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getderef(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(27);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(27);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(27);
         var1.setline(27);
         PyObject var6 = var1.getderef(0);
         var7 = var6._in(var1.getlocal(1).__getattr__("__dict__"));
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject Hashable$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(36);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __hash__$4, (PyObject)null);
      var3 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__hash__", var3);
      var3 = null;
      var1.setline(40);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasshook__$5, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__subclasshook__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __hash__$4(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __subclasshook__$5(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Hashable"));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(44);
            var3 = var1.getlocal(1).__getattr__("__mro__").__iter__();

            while(true) {
               var1.setline(44);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(2, var4);
               var1.setline(45);
               PyString var8 = PyString.fromInterned("__hash__");
               var10000 = var8._in(var1.getlocal(2).__getattr__("__dict__"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(46);
                  if (var1.getlocal(2).__getattr__("__dict__").__getitem__(PyString.fromInterned("__hash__")).__nonzero__()) {
                     var1.setline(47);
                     var5 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var5;
                  }
                  break;
               }
            }
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("AttributeError"))) {
               throw var7;
            }

            var1.setline(51);
            if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__hash__"), (PyObject)var1.getglobal("None")).__nonzero__()) {
               var1.setline(52);
               var5 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var5;
            }
         }
      }

      var1.setline(53);
      var5 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject Iterable$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(57);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(59);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __iter__$7, (PyObject)null);
      var3 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__iter__", var3);
      var3 = null;
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasshook__$8, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__subclasshook__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __iter__$7(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      switch (var1.f_lasti) {
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var4 = (PyObject)var10000;
            }
         case 0:
         default:
            var1.setline(61);
            if (!var1.getglobal("False").__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(62);
               var1.setline(62);
               var4 = var1.getglobal("None");
               var1.f_lasti = 1;
               var3 = new Object[3];
               var1.f_savedlocals = var3;
               return var4;
            }
      }
   }

   public PyObject __subclasshook__$8(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Iterable"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         if (var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__iter__")).__nonzero__()) {
            var1.setline(68);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(69);
      var3 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Iterator$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, next$10, (PyObject)null);
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$11, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __subclasshook__$12, (PyObject)null);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__subclasshook__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject next$10(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      throw Py.makeException(var1.getglobal("StopIteration"));
   }

   public PyObject __iter__$11(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __subclasshook__$12(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Iterator"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var10000 = var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("next"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__iter__"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(87);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(88);
      var3 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Sized$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(92);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(94);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __len__$14, (PyObject)null);
      var3 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__len__", var3);
      var3 = null;
      var1.setline(98);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasshook__$15, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__subclasshook__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __len__$14(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __subclasshook__$15(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Sized"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(101);
         if (var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__len__")).__nonzero__()) {
            var1.setline(102);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(103);
      var3 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Container$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(107);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(109);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __contains__$17, (PyObject)null);
      var3 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__contains__", var3);
      var3 = null;
      var1.setline(113);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasshook__$18, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__subclasshook__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __contains__$17(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __subclasshook__$18(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Container"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         if (var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__contains__")).__nonzero__()) {
            var1.setline(117);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(118);
      var3 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Callable$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(122);
      PyObject var3 = var1.getname("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(124);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __call__$20, (PyObject)null);
      var3 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__call__", var3);
      var3 = null;
      var1.setline(128);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasshook__$21, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("__subclasshook__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __call__$20(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __subclasshook__$21(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("Callable"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(131);
         if (var1.getglobal("_hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
            var1.setline(132);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(133);
      var3 = var1.getglobal("NotImplemented");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Set$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A set is a finite, iterable container.\n\n    This class provides concrete generic implementations of all\n    methods except for __contains__, __iter__ and __len__.\n\n    To override the comparisons (presumably for speed, as the\n    semantics are fixed), all you have to do is redefine __le__ and\n    then the other operations will automatically follow suit.\n    "));
      var1.setline(148);
      PyString.fromInterned("A set is a finite, iterable container.\n\n    This class provides concrete generic implementations of all\n    methods except for __contains__, __iter__ and __len__.\n\n    To override the comparisons (presumably for speed, as the\n    semantics are fixed), all you have to do is redefine __le__ and\n    then the other operations will automatically follow suit.\n    ");
      var1.setline(150);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __le__$23, (PyObject)null);
      var1.setlocal("__le__", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __lt__$24, (PyObject)null);
      var1.setlocal("__lt__", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __gt__$25, (PyObject)null);
      var1.setlocal("__gt__", var4);
      var3 = null;
      var1.setline(170);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ge__$26, (PyObject)null);
      var1.setlocal("__ge__", var4);
      var3 = null;
      var1.setline(175);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$27, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(180);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$28, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _from_iterable$29, PyString.fromInterned("Construct an instance of the class from any iterable input.\n\n        Must override this method if the class constructor signature\n        does not accept an iterable for an input.\n        "));
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_from_iterable", var5);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __and__$30, (PyObject)null);
      var1.setlocal("__and__", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isdisjoint$32, (PyObject)null);
      var1.setlocal("isdisjoint", var4);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __or__$33, (PyObject)null);
      var1.setlocal("__or__", var4);
      var3 = null;
      var1.setline(209);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __sub__$35, (PyObject)null);
      var1.setlocal("__sub__", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __xor__$37, (PyObject)null);
      var1.setlocal("__xor__", var4);
      var3 = null;
      var1.setline(225);
      var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(227);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _hash$38, PyString.fromInterned("Compute the hash value of a set.\n\n        Note that we don't define __hash__: not all sets are hashable.\n        But if you define a hashable set type, its __hash__ should\n        call this function.\n\n        This must be compatible __eq__.\n\n        All sets ought to compare equal if they contain the same\n        elements, regardless of how they are implemented, and\n        regardless of the order of the elements; so there's not much\n        freedom for __eq__ or __hash__.  We match the algorithm used\n        by the built-in frozenset type.\n        "));
      var1.setlocal("_hash", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __le__$23(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(152);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(153);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._gt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(154);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(155);
            var4 = var1.getlocal(0).__iter__();

            do {
               var1.setline(155);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(158);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(2, var5);
               var1.setline(156);
               PyObject var6 = var1.getlocal(2);
               var10000 = var6._notin(var1.getlocal(1));
               var6 = null;
            } while(!var10000.__nonzero__());

            var1.setline(157);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __lt__$24(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(162);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(163);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("__le__").__call__(var2, var1.getlocal(1));
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __gt__$25(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(167);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(168);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._lt(var1.getlocal(0));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ge__$26(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(172);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(173);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._le(var1.getlocal(0));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __eq__$27(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(177);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(178);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("__le__").__call__(var2, var1.getlocal(1));
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$28(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _from_iterable$29(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Construct an instance of the class from any iterable input.\n\n        Must override this method if the class constructor signature\n        does not accept an iterable for an input.\n        ");
      var1.setline(190);
      PyObject var3 = var1.getlocal(0).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __and__$30(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(193);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Iterable")).__not__().__nonzero__()) {
         var1.setline(194);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(195);
         PyObject var10000 = var1.getderef(0).__getattr__("_from_iterable");
         var1.setline(195);
         PyObject var10004 = var1.f_globals;
         PyObject[] var4 = Py.EmptyObjects;
         PyCode var10006 = f$31;
         PyObject[] var5 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10004, var4, var10006, (PyObject)null, var5);
         PyObject var10002 = var6.__call__(var2, var1.getlocal(1).__iter__());
         Arrays.fill(var4, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$31(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(195);
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

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(195);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(195);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._in(var1.getderef(0));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(195);
      var1.setline(195);
      var7 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject isdisjoint$32(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getlocal(1).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(198);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(201);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(199);
         var5 = var1.getlocal(2);
         var10000 = var5._in(var1.getlocal(0));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(200);
      var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __or__$33(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Iterable")).__not__().__nonzero__()) {
         var1.setline(205);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(206);
         var1.setline(206);
         PyObject[] var4 = Py.EmptyObjects;
         PyFunction var5 = new PyFunction(var1.f_globals, var4, f$34, (PyObject)null);
         PyObject var10000 = var5.__call__(var2, (new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})).__iter__());
         Arrays.fill(var4, (Object)null);
         PyObject var6 = var10000;
         var1.setlocal(2, var6);
         var4 = null;
         var1.setline(207);
         var3 = var1.getlocal(0).__getattr__("_from_iterable").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$34(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(206);
            var3 = var1.getlocal(0).__iter__();
            var1.setline(206);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(206);
            var5 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(206);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(1, var6);
            var1.setline(206);
            var1.setline(206);
            var8 = var1.getlocal(1);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var8;
         }

         var1.setline(206);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(206);
         var5 = var1.getlocal(2).__iter__();
      }
   }

   public PyObject __sub__$35(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(210);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(211);
         if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0), var1.getglobal("Iterable")).__not__().__nonzero__()) {
            var1.setline(212);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(213);
         PyObject var4 = var1.getlocal(0).__getattr__("_from_iterable").__call__(var2, var1.getderef(0));
         var1.setderef(0, var4);
         var4 = null;
      }

      var1.setline(214);
      PyObject var10000 = var1.getlocal(0).__getattr__("_from_iterable");
      var1.setline(214);
      PyObject var10004 = var1.f_globals;
      PyObject[] var6 = Py.EmptyObjects;
      PyCode var10006 = f$36;
      PyObject[] var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10004, var6, var10006, (PyObject)null, var5);
      PyObject var10002 = var7.__call__(var2, var1.getlocal(0).__iter__());
      Arrays.fill(var6, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$36(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(214);
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

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(214);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(215);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._notin(var1.getderef(0));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(214);
      var1.setline(214);
      var7 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject __xor__$37(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
         var1.setline(219);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Iterable")).__not__().__nonzero__()) {
            var1.setline(220);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(221);
         PyObject var4 = var1.getlocal(0).__getattr__("_from_iterable").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(222);
      var3 = var1.getlocal(0)._sub(var1.getlocal(1))._or(var1.getlocal(1)._sub(var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _hash$38(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("Compute the hash value of a set.\n\n        Note that we don't define __hash__: not all sets are hashable.\n        But if you define a hashable set type, its __hash__ should\n        call this function.\n\n        This must be compatible __eq__.\n\n        All sets ought to compare equal if they contain the same\n        elements, regardless of how they are implemented, and\n        regardless of the order of the elements; so there's not much\n        freedom for __eq__ or __hash__.  We match the algorithm used\n        by the built-in frozenset type.\n        ");
      var1.setline(242);
      PyObject var3 = var1.getglobal("sys").__getattr__("maxint");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(243);
      var3 = Py.newInteger(2)._mul(var1.getlocal(1))._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(245);
      var3 = Py.newInteger(1927868237)._mul(var1.getlocal(3)._add(Py.newInteger(1)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(246);
      var3 = var1.getlocal(4);
      var3 = var3._iand(var1.getlocal(2));
      var1.setlocal(4, var3);
      var1.setline(247);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(247);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(251);
            var3 = var1.getlocal(4)._mul(Py.newInteger(69069))._add(Py.newInteger(907133923));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(252);
            var3 = var1.getlocal(4);
            var3 = var3._iand(var1.getlocal(2));
            var1.setlocal(4, var3);
            var1.setline(253);
            var3 = var1.getlocal(4);
            PyObject var10000 = var3._gt(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(254);
               var3 = var1.getlocal(4);
               var3 = var3._isub(var1.getlocal(2)._add(Py.newInteger(1)));
               var1.setlocal(4, var3);
            }

            var1.setline(255);
            var3 = var1.getlocal(4);
            var10000 = var3._eq(Py.newInteger(-1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(256);
               PyInteger var6 = Py.newInteger(590923713);
               var1.setlocal(4, var6);
               var3 = null;
            }

            var1.setline(257);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(248);
         PyObject var5 = var1.getglobal("hash").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(249);
         var5 = var1.getlocal(4);
         var5 = var5._ixor(var1.getlocal(6)._xor(var1.getlocal(6)._lshift(Py.newInteger(16)))._xor(Py.newInteger(89869747))._mul(Py.newLong("3644798167")));
         var1.setlocal(4, var5);
         var1.setline(250);
         var5 = var1.getlocal(4);
         var5 = var5._iand(var1.getlocal(2));
         var1.setlocal(4, var5);
      }
   }

   public PyObject MutableSet$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(264);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, add$40, PyString.fromInterned("Add an element."));
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("add", var5);
      var3 = null;
      var1.setline(269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, discard$41, PyString.fromInterned("Remove an element.  Do not raise an exception if absent."));
      var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("discard", var5);
      var3 = null;
      var1.setline(274);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$42, PyString.fromInterned("Remove an element. If not a member, raise a KeyError."));
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(280);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop$43, PyString.fromInterned("Return the popped value.  Raise KeyError if empty."));
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$44, PyString.fromInterned("This is slow (creates N new iterators!) but effective."));
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ior__$45, (PyObject)null);
      var1.setlocal("__ior__", var4);
      var3 = null;
      var1.setline(303);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iand__$46, (PyObject)null);
      var1.setlocal("__iand__", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ixor__$47, (PyObject)null);
      var1.setlocal("__ixor__", var4);
      var3 = null;
      var1.setline(321);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __isub__$48, (PyObject)null);
      var1.setlocal("__isub__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject add$40(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyString.fromInterned("Add an element.");
      var1.setline(267);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject discard$41(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Remove an element.  Do not raise an exception if absent.");
      var1.setline(272);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject remove$42(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyString.fromInterned("Remove an element. If not a member, raise a KeyError.");
      var1.setline(276);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(277);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(278);
         var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject pop$43(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("Return the popped value.  Raise KeyError if empty.");
      var1.setline(282);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(284);
         var3 = var1.getglobal("next").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (var5.match(var1.getglobal("StopIteration"))) {
            var1.setline(286);
            throw Py.makeException(var1.getglobal("KeyError"));
         }

         throw var5;
      }

      var1.setline(287);
      var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(2));
      var1.setline(288);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject clear$44(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("This is slow (creates N new iterators!) but effective.");

      try {
         while(true) {
            var1.setline(293);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(294);
            var1.getlocal(0).__getattr__("pop").__call__(var2);
         }
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(296);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __ior__$45(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(299);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(301);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(300);
         var1.getlocal(0).__getattr__("add").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject __iand__$46(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyObject var3 = var1.getlocal(0)._sub(var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(304);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(306);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(305);
         var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject __ixor__$47(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(310);
         var1.getlocal(0).__getattr__("clear").__call__(var2);
      } else {
         var1.setline(312);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Set")).__not__().__nonzero__()) {
            var1.setline(313);
            var3 = var1.getlocal(0).__getattr__("_from_iterable").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(314);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(314);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(315);
            PyObject var5 = var1.getlocal(2);
            var10000 = var5._in(var1.getlocal(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(316);
               var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(2));
            } else {
               var1.setline(318);
               var1.getlocal(0).__getattr__("add").__call__(var2, var1.getlocal(2));
            }
         }
      }

      var1.setline(319);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __isub__$48(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(323);
         var1.getlocal(0).__getattr__("clear").__call__(var2);
      } else {
         var1.setline(325);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(325);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(326);
            var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(2));
         }
      }

      var1.setline(327);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Mapping$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(337);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$50, (PyObject)null);
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(341);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$51, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$52, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$53, (PyObject)null);
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(358);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervalues$54, (PyObject)null);
      var1.setlocal("itervalues", var4);
      var3 = null;
      var1.setline(362);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iteritems$55, (PyObject)null);
      var1.setlocal("iteritems", var4);
      var3 = null;
      var1.setline(366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$56, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(369);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$57, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(372);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$58, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(376);
      var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$59, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(383);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$60, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$50(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      throw Py.makeException(var1.getglobal("KeyError"));
   }

   public PyObject get$51(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(343);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(345);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __contains__$52(PyFrame var1, ThreadState var2) {
      PyObject var4;
      try {
         var1.setline(349);
         var1.getlocal(0).__getitem__(var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(351);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(353);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject iterkeys$53(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itervalues$54(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(359);
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

      var1.setline(359);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(360);
         var1.setline(360);
         var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject iteritems$55(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(363);
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

            PyObject var7 = (PyObject)var10000;
      }

      var1.setline(363);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(364);
         var1.setline(364);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getitem__(var1.getlocal(1))};
         PyTuple var8 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject keys$56(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$57(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(370);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(370);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(370);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getitem__(var1.getlocal(2))})));
      }
   }

   public PyObject values$58(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(373);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(373);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(373);
         var1.getlocal(1).__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)));
      }
   }

   public PyObject __eq__$59(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Mapping")).__not__().__nonzero__()) {
         var1.setline(380);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(381);
         PyObject var4 = var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("items").__call__(var2));
         PyObject var10000 = var4._eq(var1.getglobal("dict").__call__(var2, var1.getlocal(1).__getattr__("items").__call__(var2)));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$60(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MappingView$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(388);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$62, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$63, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$64, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$62(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_mapping", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$63(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_mapping"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$64(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = PyString.fromInterned("{0.__class__.__name__}({0._mapping!r})").__getattr__("format").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject KeysView$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(400);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _from_iterable$66, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_from_iterable", var5);
      var3 = null;
      var1.setline(404);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$67, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$68, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _from_iterable$66(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyObject var3 = var1.getglobal("set").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$67(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_mapping"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$68(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(408);
            var3 = var1.getlocal(0).__getattr__("_mapping").__iter__();
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

      var1.setline(408);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(409);
         var1.setline(409);
         var6 = var1.getlocal(1);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject ItemsView$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(414);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _from_iterable$70, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_from_iterable", var5);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$71, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(427);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$72, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _from_iterable$70(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getglobal("set").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$71(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;

      PyObject var8;
      try {
         var1.setline(421);
         var3 = var1.getlocal(0).__getattr__("_mapping").__getitem__(var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("KeyError"))) {
            var1.setline(423);
            var8 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var8;
         }

         throw var7;
      }

      var1.setline(425);
      var5 = var1.getlocal(4);
      PyObject var10000 = var5._eq(var1.getlocal(3));
      var5 = null;
      var8 = var10000;
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject __iter__$72(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(428);
            var3 = var1.getlocal(0).__getattr__("_mapping").__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var7 = (PyObject)var10000;
      }

      var1.setline(428);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(429);
         var1.setline(429);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_mapping").__getitem__(var1.getlocal(1))};
         PyTuple var8 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject ValuesView$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(434);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __contains__$74, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(440);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$75, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __contains__$74(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyObject var3 = var1.getlocal(0).__getattr__("_mapping").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(435);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(438);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(436);
         var5 = var1.getlocal(1);
         var10000 = var5._eq(var1.getlocal(0).__getattr__("_mapping").__getitem__(var1.getlocal(2)));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(437);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __iter__$75(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(441);
            var3 = var1.getlocal(0).__getattr__("_mapping").__iter__();
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

      var1.setline(441);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(442);
         var1.setline(442);
         var6 = var1.getlocal(0).__getattr__("_mapping").__getitem__(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject MutableMapping$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(447);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __setitem__$77, (PyObject)null);
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$78, (PyObject)null);
      var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(455);
      var5 = var1.getname("object").__call__(var2);
      var1.setlocal("_MutableMapping__marker", var5);
      var3 = null;
      var1.setline(457);
      var3 = new PyObject[]{var1.getname("_MutableMapping__marker")};
      var4 = new PyFunction(var1.f_globals, var3, pop$79, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(468);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, popitem$80, (PyObject)null);
      var1.setlocal("popitem", var4);
      var3 = null;
      var1.setline(477);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$81, (PyObject)null);
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(484);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, update$82, (PyObject)null);
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(505);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$83, (PyObject)null);
      var1.setlocal("setdefault", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __setitem__$77(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      throw Py.makeException(var1.getglobal("KeyError"));
   }

   public PyObject __delitem__$78(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      throw Py.makeException(var1.getglobal("KeyError"));
   }

   public PyObject pop$79(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(459);
         PyObject var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(461);
            var4 = var1.getlocal(2);
            PyObject var10000 = var4._is(var1.getlocal(0).__getattr__("_MutableMapping__marker"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(462);
               throw Py.makeException();
            }

            var1.setline(463);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(465);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(466);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject popitem$80(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var5;
      try {
         var1.setline(470);
         var5 = var1.getglobal("next").__call__(var2, var1.getglobal("iter").__call__(var2, var1.getlocal(0)));
         var1.setlocal(1, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("StopIteration"))) {
            var1.setline(472);
            throw Py.makeException(var1.getglobal("KeyError"));
         }

         throw var3;
      }

      var1.setline(473);
      var5 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(474);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(475);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject clear$81(PyFrame var1, ThreadState var2) {
      try {
         while(true) {
            var1.setline(479);
            if (var1.getglobal("True").__nonzero__()) {
               var1.setline(480);
               var1.getlocal(0).__getattr__("popitem").__call__(var2);
               continue;
            }
         }
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(482);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject update$82(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(486);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("update() takes at most 2 positional arguments ({} given)").__getattr__("format").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(488);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(489);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("update() takes at least 1 argument (0 given)")));
         } else {
            var1.setline(490);
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(491);
            var1.setline(491);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._ge(Py.newInteger(2));
            var3 = null;
            Object var7 = var10000.__nonzero__() ? var1.getlocal(0).__getitem__(Py.newInteger(1)) : new PyTuple(Py.EmptyObjects);
            var1.setlocal(3, (PyObject)var7);
            var3 = null;
            var1.setline(493);
            PyObject var4;
            PyObject var5;
            PyObject var6;
            PyObject[] var8;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Mapping")).__nonzero__()) {
               var1.setline(494);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(494);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(495);
                  var5 = var1.getlocal(3).__getitem__(var1.getlocal(4));
                  var1.getlocal(2).__setitem__(var1.getlocal(4), var5);
                  var5 = null;
               }
            } else {
               var1.setline(496);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("keys")).__nonzero__()) {
                  var1.setline(497);
                  var3 = var1.getlocal(3).__getattr__("keys").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(497);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(4, var4);
                     var1.setline(498);
                     var5 = var1.getlocal(3).__getitem__(var1.getlocal(4));
                     var1.getlocal(2).__setitem__(var1.getlocal(4), var5);
                     var5 = null;
                  }
               } else {
                  var1.setline(500);
                  var3 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(500);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var8 = Py.unpackSequence(var4, 2);
                     var6 = var8[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var8[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var1.setline(501);
                     var5 = var1.getlocal(5);
                     var1.getlocal(2).__setitem__(var1.getlocal(4), var5);
                     var5 = null;
                  }
               }
            }

            var1.setline(502);
            var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(502);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var8 = Py.unpackSequence(var4, 2);
               var6 = var8[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var8[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(503);
               var5 = var1.getlocal(5);
               var1.getlocal(2).__setitem__(var1.getlocal(4), var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject setdefault$83(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(507);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(509);
            PyObject var5 = var1.getlocal(2);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var5);
            var5 = null;
            var1.setline(510);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject Sequence$84(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("All the operations on a read-only sequence.\n\n    Concrete subclasses must override __new__ or __init__,\n    __getitem__, and __len__.\n    "));
      var1.setline(523);
      PyString.fromInterned("All the operations on a read-only sequence.\n\n    Concrete subclasses must override __new__ or __init__,\n    __getitem__, and __len__.\n    ");
      var1.setline(525);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$85, (PyObject)null);
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(529);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$86, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(539);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$87, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(545);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __reversed__$88, (PyObject)null);
      var1.setlocal("__reversed__", var4);
      var3 = null;
      var1.setline(549);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, index$89, (PyObject)null);
      var1.setlocal("index", var4);
      var3 = null;
      var1.setline(555);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, count$90, (PyObject)null);
      var1.setlocal("count", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$85(PyFrame var1, ThreadState var2) {
      var1.setline(527);
      throw Py.makeException(var1.getglobal("IndexError"));
   }

   public PyObject __iter__$86(PyFrame var1, ThreadState var2) {
      Throwable var10000;
      label45: {
         boolean var10001;
         Object[] var3;
         PyObject var7;
         PyObject var11;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(530);
               PyInteger var8 = Py.newInteger(0);
               var1.setlocal(1, var8);
               var3 = null;
               break;
            case 1:
               var3 = var1.f_savedlocals;

               try {
                  Object var10 = var1.getGeneratorInput();
                  if (var10 instanceof PyException) {
                     throw (Throwable)var10;
                  }

                  var11 = (PyObject)var10;
                  var1.setline(535);
                  var7 = var1.getlocal(1);
                  var7 = var7._iadd(Py.newInteger(1));
                  var1.setlocal(1, var7);
               } catch (Throwable var5) {
                  var10000 = var5;
                  var10001 = false;
                  break label45;
               }
         }

         label40: {
            try {
               var1.setline(532);
               if (var1.getglobal("True").__nonzero__()) {
                  break label40;
               }
            } catch (Throwable var6) {
               var10000 = var6;
               var10001 = false;
               break label45;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         try {
            var1.setline(533);
            var7 = var1.getlocal(0).__getitem__(var1.getlocal(1));
            var1.setlocal(2, var7);
            var3 = null;
            var1.setline(534);
            var1.setline(534);
            var11 = var1.getlocal(2);
            var1.f_lasti = 1;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var11;
         } catch (Throwable var4) {
            var10000 = var4;
            var10001 = false;
         }
      }

      PyException var9 = Py.setException(var10000, var1);
      if (var9.match(var1.getglobal("IndexError"))) {
         var1.setline(537);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         throw var9;
      }
   }

   public PyObject __contains__$87(PyFrame var1, ThreadState var2) {
      var1.setline(540);
      PyObject var3 = var1.getlocal(0).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(540);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(543);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(541);
         var5 = var1.getlocal(2);
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(542);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __reversed__$88(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(546);
            var3 = var1.getglobal("reversed").__call__(var2, var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)))).__iter__();
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

      var1.setline(546);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(547);
         var1.setline(547);
         var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject index$89(PyFrame var1, ThreadState var2) {
      var1.setline(550);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(550);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(553);
            throw Py.makeException(var1.getglobal("ValueError"));
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(551);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(552);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject count$90(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(556);
      PyObject var10000 = var1.getglobal("sum");
      var1.setline(556);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$91;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(0).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$91(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(556);
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

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(556);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(556);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._eq(var1.getderef(0));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(556);
      var1.setline(556);
      PyInteger var8 = Py.newInteger(1);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var8;
   }

   public PyObject MutableSequence$92(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(566);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __setitem__$93, (PyObject)null);
      PyObject var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(570);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$94, (PyObject)null);
      var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insert$95, (PyObject)null);
      var5 = var1.getname("abstractmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("insert", var5);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$96, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(581);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reverse$97, (PyObject)null);
      var1.setlocal("reverse", var4);
      var3 = null;
      var1.setline(586);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, extend$98, (PyObject)null);
      var1.setlocal("extend", var4);
      var3 = null;
      var1.setline(590);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, pop$99, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(595);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$100, (PyObject)null);
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(598);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iadd__$101, (PyObject)null);
      var1.setlocal("__iadd__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __setitem__$93(PyFrame var1, ThreadState var2) {
      var1.setline(568);
      throw Py.makeException(var1.getglobal("IndexError"));
   }

   public PyObject __delitem__$94(PyFrame var1, ThreadState var2) {
      var1.setline(572);
      throw Py.makeException(var1.getglobal("IndexError"));
   }

   public PyObject insert$95(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      throw Py.makeException(var1.getglobal("IndexError"));
   }

   public PyObject append$96(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      var1.getlocal(0).__getattr__("insert").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reverse$97(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(583);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(1)._floordiv(Py.newInteger(2))).__iter__();

      while(true) {
         var1.setline(583);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(584);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(var1.getlocal(1)._sub(var1.getlocal(2))._sub(Py.newInteger(1))), var1.getlocal(0).__getitem__(var1.getlocal(2))});
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.getlocal(0).__setitem__(var1.getlocal(2), var7);
         var7 = null;
         var7 = var6[1];
         var1.getlocal(0).__setitem__(var1.getlocal(1)._sub(var1.getlocal(2))._sub(Py.newInteger(1)), var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject extend$98(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(587);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(588);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject pop$99(PyFrame var1, ThreadState var2) {
      var1.setline(591);
      PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(592);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(593);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$100(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      var1.getlocal(0).__delitem__(var1.getlocal(0).__getattr__("index").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iadd__$101(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      var1.getlocal(0).__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.setline(600);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public _abcoll$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"C", "attr", "_(27_19)"};
      String[] var10001 = var2;
      _abcoll$py var10007 = self;
      var2 = new String[]{"attr"};
      _hasattr$1 = Py.newCode(2, var10001, var1, "_hasattr", 25, false, false, var10007, 1, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "B"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"attr"};
      f$2 = Py.newCode(1, var10001, var1, "<genexpr>", 27, false, false, var10007, 2, (String[])null, var2, 0, 4129);
      var2 = new String[0];
      Hashable$3 = Py.newCode(0, var2, var1, "Hashable", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __hash__$4 = Py.newCode(1, var2, var1, "__hash__", 36, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "C", "B"};
      __subclasshook__$5 = Py.newCode(2, var2, var1, "__subclasshook__", 40, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Iterable$6 = Py.newCode(0, var2, var1, "Iterable", 56, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __iter__$7 = Py.newCode(1, var2, var1, "__iter__", 59, false, false, self, 7, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"cls", "C"};
      __subclasshook__$8 = Py.newCode(2, var2, var1, "__subclasshook__", 64, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Iterator$9 = Py.newCode(0, var2, var1, "Iterator", 74, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      next$10 = Py.newCode(1, var2, var1, "next", 76, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$11 = Py.newCode(1, var2, var1, "__iter__", 80, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "C"};
      __subclasshook__$12 = Py.newCode(2, var2, var1, "__subclasshook__", 83, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Sized$13 = Py.newCode(0, var2, var1, "Sized", 91, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __len__$14 = Py.newCode(1, var2, var1, "__len__", 94, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "C"};
      __subclasshook__$15 = Py.newCode(2, var2, var1, "__subclasshook__", 98, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Container$16 = Py.newCode(0, var2, var1, "Container", 106, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "x"};
      __contains__$17 = Py.newCode(2, var2, var1, "__contains__", 109, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "C"};
      __subclasshook__$18 = Py.newCode(2, var2, var1, "__subclasshook__", 113, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Callable$19 = Py.newCode(0, var2, var1, "Callable", 121, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwds"};
      __call__$20 = Py.newCode(3, var2, var1, "__call__", 124, true, true, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "C"};
      __subclasshook__$21 = Py.newCode(2, var2, var1, "__subclasshook__", 128, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Set$22 = Py.newCode(0, var2, var1, "Set", 139, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "other", "elem"};
      __le__$23 = Py.newCode(2, var2, var1, "__le__", 150, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$24 = Py.newCode(2, var2, var1, "__lt__", 160, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __gt__$25 = Py.newCode(2, var2, var1, "__gt__", 165, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ge__$26 = Py.newCode(2, var2, var1, "__ge__", 170, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$27 = Py.newCode(2, var2, var1, "__eq__", 175, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$28 = Py.newCode(2, var2, var1, "__ne__", 180, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "it"};
      _from_iterable$29 = Py.newCode(2, var2, var1, "_from_iterable", 183, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "_(195_35)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      __and__$30 = Py.newCode(2, var10001, var1, "__and__", 192, false, false, var10007, 30, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      f$31 = Py.newCode(1, var10001, var1, "<genexpr>", 195, false, false, var10007, 31, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "other", "value"};
      isdisjoint$32 = Py.newCode(2, var2, var1, "isdisjoint", 197, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "chain", "_(206_17)"};
      __or__$33 = Py.newCode(2, var2, var1, "__or__", 203, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "e", "s"};
      f$34 = Py.newCode(1, var2, var1, "<genexpr>", 206, false, false, self, 34, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "other", "_(214_35)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"other"};
      __sub__$35 = Py.newCode(2, var10001, var1, "__sub__", 209, false, false, var10007, 35, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"other"};
      f$36 = Py.newCode(1, var10001, var1, "<genexpr>", 214, false, false, var10007, 36, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "other"};
      __xor__$37 = Py.newCode(2, var2, var1, "__xor__", 217, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "MAX", "MASK", "n", "h", "x", "hx"};
      _hash$38 = Py.newCode(1, var2, var1, "_hash", 227, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MutableSet$39 = Py.newCode(0, var2, var1, "MutableSet", 262, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      add$40 = Py.newCode(2, var2, var1, "add", 264, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      discard$41 = Py.newCode(2, var2, var1, "discard", 269, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      remove$42 = Py.newCode(2, var2, var1, "remove", 274, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "it", "value"};
      pop$43 = Py.newCode(1, var2, var1, "pop", 280, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$44 = Py.newCode(1, var2, var1, "clear", 290, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "it", "value"};
      __ior__$45 = Py.newCode(2, var2, var1, "__ior__", 298, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "it", "value"};
      __iand__$46 = Py.newCode(2, var2, var1, "__iand__", 303, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "it", "value"};
      __ixor__$47 = Py.newCode(2, var2, var1, "__ixor__", 308, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "it", "value"};
      __isub__$48 = Py.newCode(2, var2, var1, "__isub__", 321, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Mapping$49 = Py.newCode(0, var2, var1, "Mapping", 335, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key"};
      __getitem__$50 = Py.newCode(2, var2, var1, "__getitem__", 337, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$51 = Py.newCode(3, var2, var1, "get", 341, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$52 = Py.newCode(2, var2, var1, "__contains__", 347, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iterkeys$53 = Py.newCode(1, var2, var1, "iterkeys", 355, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      itervalues$54 = Py.newCode(1, var2, var1, "itervalues", 358, false, false, self, 54, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key"};
      iteritems$55 = Py.newCode(1, var2, var1, "iteritems", 362, false, false, self, 55, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      keys$56 = Py.newCode(1, var2, var1, "keys", 366, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[370_16]", "key"};
      items$57 = Py.newCode(1, var2, var1, "items", 369, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[373_16]", "key"};
      values$58 = Py.newCode(1, var2, var1, "values", 372, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$59 = Py.newCode(2, var2, var1, "__eq__", 378, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$60 = Py.newCode(2, var2, var1, "__ne__", 383, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MappingView$61 = Py.newCode(0, var2, var1, "MappingView", 386, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mapping"};
      __init__$62 = Py.newCode(2, var2, var1, "__init__", 388, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$63 = Py.newCode(1, var2, var1, "__len__", 391, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$64 = Py.newCode(1, var2, var1, "__repr__", 394, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      KeysView$65 = Py.newCode(0, var2, var1, "KeysView", 398, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "it"};
      _from_iterable$66 = Py.newCode(2, var2, var1, "_from_iterable", 400, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$67 = Py.newCode(2, var2, var1, "__contains__", 404, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __iter__$68 = Py.newCode(1, var2, var1, "__iter__", 407, false, false, self, 68, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      ItemsView$69 = Py.newCode(0, var2, var1, "ItemsView", 412, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "it"};
      _from_iterable$70 = Py.newCode(2, var2, var1, "_from_iterable", 414, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item", "key", "value", "v"};
      __contains__$71 = Py.newCode(2, var2, var1, "__contains__", 418, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __iter__$72 = Py.newCode(1, var2, var1, "__iter__", 427, false, false, self, 72, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      ValuesView$73 = Py.newCode(0, var2, var1, "ValuesView", 432, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "key"};
      __contains__$74 = Py.newCode(2, var2, var1, "__contains__", 434, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __iter__$75 = Py.newCode(1, var2, var1, "__iter__", 440, false, false, self, 75, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      MutableMapping$76 = Py.newCode(0, var2, var1, "MutableMapping", 445, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value"};
      __setitem__$77 = Py.newCode(3, var2, var1, "__setitem__", 447, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$78 = Py.newCode(2, var2, var1, "__delitem__", 451, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "value"};
      pop$79 = Py.newCode(3, var2, var1, "pop", 457, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      popitem$80 = Py.newCode(1, var2, var1, "popitem", 468, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$81 = Py.newCode(1, var2, var1, "clear", 477, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwds", "self", "other", "key", "value"};
      update$82 = Py.newCode(2, var2, var1, "update", 484, true, true, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      setdefault$83 = Py.newCode(3, var2, var1, "setdefault", 505, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Sequence$84 = Py.newCode(0, var2, var1, "Sequence", 518, false, false, self, 84, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "index"};
      __getitem__$85 = Py.newCode(2, var2, var1, "__getitem__", 525, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "v"};
      __iter__$86 = Py.newCode(1, var2, var1, "__iter__", 529, false, false, self, 86, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "value", "v"};
      __contains__$87 = Py.newCode(2, var2, var1, "__contains__", 539, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __reversed__$88 = Py.newCode(1, var2, var1, "__reversed__", 545, false, false, self, 88, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "value", "i", "v"};
      index$89 = Py.newCode(2, var2, var1, "index", 549, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "_(556_19)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"value"};
      count$90 = Py.newCode(2, var10001, var1, "count", 555, false, false, var10007, 90, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "v"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"value"};
      f$91 = Py.newCode(1, var10001, var1, "<genexpr>", 556, false, false, var10007, 91, (String[])null, var2, 0, 4129);
      var2 = new String[0];
      MutableSequence$92 = Py.newCode(0, var2, var1, "MutableSequence", 564, false, false, self, 92, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "index", "value"};
      __setitem__$93 = Py.newCode(3, var2, var1, "__setitem__", 566, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __delitem__$94 = Py.newCode(2, var2, var1, "__delitem__", 570, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "value"};
      insert$95 = Py.newCode(3, var2, var1, "insert", 574, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      append$96 = Py.newCode(2, var2, var1, "append", 578, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "i"};
      reverse$97 = Py.newCode(1, var2, var1, "reverse", 581, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "values", "v"};
      extend$98 = Py.newCode(2, var2, var1, "extend", 586, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "v"};
      pop$99 = Py.newCode(2, var2, var1, "pop", 590, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      remove$100 = Py.newCode(2, var2, var1, "remove", 595, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "values"};
      __iadd__$101 = Py.newCode(2, var2, var1, "__iadd__", 598, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _abcoll$py("_abcoll$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_abcoll$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._hasattr$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.Hashable$3(var2, var3);
         case 4:
            return this.__hash__$4(var2, var3);
         case 5:
            return this.__subclasshook__$5(var2, var3);
         case 6:
            return this.Iterable$6(var2, var3);
         case 7:
            return this.__iter__$7(var2, var3);
         case 8:
            return this.__subclasshook__$8(var2, var3);
         case 9:
            return this.Iterator$9(var2, var3);
         case 10:
            return this.next$10(var2, var3);
         case 11:
            return this.__iter__$11(var2, var3);
         case 12:
            return this.__subclasshook__$12(var2, var3);
         case 13:
            return this.Sized$13(var2, var3);
         case 14:
            return this.__len__$14(var2, var3);
         case 15:
            return this.__subclasshook__$15(var2, var3);
         case 16:
            return this.Container$16(var2, var3);
         case 17:
            return this.__contains__$17(var2, var3);
         case 18:
            return this.__subclasshook__$18(var2, var3);
         case 19:
            return this.Callable$19(var2, var3);
         case 20:
            return this.__call__$20(var2, var3);
         case 21:
            return this.__subclasshook__$21(var2, var3);
         case 22:
            return this.Set$22(var2, var3);
         case 23:
            return this.__le__$23(var2, var3);
         case 24:
            return this.__lt__$24(var2, var3);
         case 25:
            return this.__gt__$25(var2, var3);
         case 26:
            return this.__ge__$26(var2, var3);
         case 27:
            return this.__eq__$27(var2, var3);
         case 28:
            return this.__ne__$28(var2, var3);
         case 29:
            return this._from_iterable$29(var2, var3);
         case 30:
            return this.__and__$30(var2, var3);
         case 31:
            return this.f$31(var2, var3);
         case 32:
            return this.isdisjoint$32(var2, var3);
         case 33:
            return this.__or__$33(var2, var3);
         case 34:
            return this.f$34(var2, var3);
         case 35:
            return this.__sub__$35(var2, var3);
         case 36:
            return this.f$36(var2, var3);
         case 37:
            return this.__xor__$37(var2, var3);
         case 38:
            return this._hash$38(var2, var3);
         case 39:
            return this.MutableSet$39(var2, var3);
         case 40:
            return this.add$40(var2, var3);
         case 41:
            return this.discard$41(var2, var3);
         case 42:
            return this.remove$42(var2, var3);
         case 43:
            return this.pop$43(var2, var3);
         case 44:
            return this.clear$44(var2, var3);
         case 45:
            return this.__ior__$45(var2, var3);
         case 46:
            return this.__iand__$46(var2, var3);
         case 47:
            return this.__ixor__$47(var2, var3);
         case 48:
            return this.__isub__$48(var2, var3);
         case 49:
            return this.Mapping$49(var2, var3);
         case 50:
            return this.__getitem__$50(var2, var3);
         case 51:
            return this.get$51(var2, var3);
         case 52:
            return this.__contains__$52(var2, var3);
         case 53:
            return this.iterkeys$53(var2, var3);
         case 54:
            return this.itervalues$54(var2, var3);
         case 55:
            return this.iteritems$55(var2, var3);
         case 56:
            return this.keys$56(var2, var3);
         case 57:
            return this.items$57(var2, var3);
         case 58:
            return this.values$58(var2, var3);
         case 59:
            return this.__eq__$59(var2, var3);
         case 60:
            return this.__ne__$60(var2, var3);
         case 61:
            return this.MappingView$61(var2, var3);
         case 62:
            return this.__init__$62(var2, var3);
         case 63:
            return this.__len__$63(var2, var3);
         case 64:
            return this.__repr__$64(var2, var3);
         case 65:
            return this.KeysView$65(var2, var3);
         case 66:
            return this._from_iterable$66(var2, var3);
         case 67:
            return this.__contains__$67(var2, var3);
         case 68:
            return this.__iter__$68(var2, var3);
         case 69:
            return this.ItemsView$69(var2, var3);
         case 70:
            return this._from_iterable$70(var2, var3);
         case 71:
            return this.__contains__$71(var2, var3);
         case 72:
            return this.__iter__$72(var2, var3);
         case 73:
            return this.ValuesView$73(var2, var3);
         case 74:
            return this.__contains__$74(var2, var3);
         case 75:
            return this.__iter__$75(var2, var3);
         case 76:
            return this.MutableMapping$76(var2, var3);
         case 77:
            return this.__setitem__$77(var2, var3);
         case 78:
            return this.__delitem__$78(var2, var3);
         case 79:
            return this.pop$79(var2, var3);
         case 80:
            return this.popitem$80(var2, var3);
         case 81:
            return this.clear$81(var2, var3);
         case 82:
            return this.update$82(var2, var3);
         case 83:
            return this.setdefault$83(var2, var3);
         case 84:
            return this.Sequence$84(var2, var3);
         case 85:
            return this.__getitem__$85(var2, var3);
         case 86:
            return this.__iter__$86(var2, var3);
         case 87:
            return this.__contains__$87(var2, var3);
         case 88:
            return this.__reversed__$88(var2, var3);
         case 89:
            return this.index$89(var2, var3);
         case 90:
            return this.count$90(var2, var3);
         case 91:
            return this.f$91(var2, var3);
         case 92:
            return this.MutableSequence$92(var2, var3);
         case 93:
            return this.__setitem__$93(var2, var3);
         case 94:
            return this.__delitem__$94(var2, var3);
         case 95:
            return this.insert$95(var2, var3);
         case 96:
            return this.append$96(var2, var3);
         case 97:
            return this.reverse$97(var2, var3);
         case 98:
            return this.extend$98(var2, var3);
         case 99:
            return this.pop$99(var2, var3);
         case 100:
            return this.remove$100(var2, var3);
         case 101:
            return this.__iadd__$101(var2, var3);
         default:
            return null;
      }
   }
}
