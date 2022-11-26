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
@Filename("sets.py")
public class sets$py extends PyFunctionTable implements PyRunnable {
   static sets$py self;
   static final PyCode f$0;
   static final PyCode BaseSet$1;
   static final PyCode __init__$2;
   static final PyCode __len__$3;
   static final PyCode __repr__$4;
   static final PyCode _repr$5;
   static final PyCode __iter__$6;
   static final PyCode __cmp__$7;
   static final PyCode __eq__$8;
   static final PyCode __ne__$9;
   static final PyCode copy$10;
   static final PyCode __deepcopy__$11;
   static final PyCode __or__$12;
   static final PyCode union$13;
   static final PyCode __and__$14;
   static final PyCode intersection$15;
   static final PyCode __xor__$16;
   static final PyCode symmetric_difference$17;
   static final PyCode __sub__$18;
   static final PyCode difference$19;
   static final PyCode __contains__$20;
   static final PyCode issubset$21;
   static final PyCode issuperset$22;
   static final PyCode __lt__$23;
   static final PyCode __gt__$24;
   static final PyCode _binary_sanity_check$25;
   static final PyCode _compute_hash$26;
   static final PyCode _update$27;
   static final PyCode ImmutableSet$28;
   static final PyCode __init__$29;
   static final PyCode __hash__$30;
   static final PyCode __getstate__$31;
   static final PyCode __setstate__$32;
   static final PyCode Set$33;
   static final PyCode __init__$34;
   static final PyCode __getstate__$35;
   static final PyCode __setstate__$36;
   static final PyCode __ior__$37;
   static final PyCode union_update$38;
   static final PyCode __iand__$39;
   static final PyCode intersection_update$40;
   static final PyCode __ixor__$41;
   static final PyCode symmetric_difference_update$42;
   static final PyCode __isub__$43;
   static final PyCode difference_update$44;
   static final PyCode update$45;
   static final PyCode clear$46;
   static final PyCode add$47;
   static final PyCode remove$48;
   static final PyCode discard$49;
   static final PyCode pop$50;
   static final PyCode __as_immutable__$51;
   static final PyCode __as_temporarily_immutable__$52;
   static final PyCode _TemporarilyImmutableSet$53;
   static final PyCode __init__$54;
   static final PyCode __hash__$55;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Classes to represent arbitrary sets (including sets of sets).\n\nThis module implements sets using dictionaries whose values are\nignored.  The usual operations (union, intersection, deletion, etc.)\nare provided as both methods and operators.\n\nImportant: sets are not sequences!  While they support 'x in s',\n'len(s)', and 'for x in s', none of those operations are unique for\nsequences; for example, mappings support all three as well.  The\ncharacteristic operation for sequences is subscripting with small\nintegers: s[i], for i in range(len(s)).  Sets don't support\nsubscripting at all.  Also, sequences allow multiple occurrences and\ntheir elements have a definite order; sets on the other hand don't\nrecord multiple occurrences and don't remember the order of element\ninsertion (which is why they don't support s[i]).\n\nThe following classes are provided:\n\nBaseSet -- All the operations common to both mutable and immutable\n    sets. This is an abstract class, not meant to be directly\n    instantiated.\n\nSet -- Mutable sets, subclass of BaseSet; not hashable.\n\nImmutableSet -- Immutable sets, subclass of BaseSet; hashable.\n    An iterable argument is mandatory to create an ImmutableSet.\n\n_TemporarilyImmutableSet -- A wrapper around a Set, hashable,\n    giving the same hash value as the immutable set equivalent\n    would have.  Do not use this class directly.\n\nOnly hashable objects can be added to a Set. In particular, you cannot\nreally add a Set as an element to another Set; if you try, what is\nactually added is an ImmutableSet built from it (it compares equal to\nthe one you tried adding).\n\nWhen you ask if `x in y' where x is a Set and y is a Set or\nImmutableSet, x is wrapped into a _TemporarilyImmutableSet z, and\nwhat's tested is actually `z in y'.\n\n"));
      var1.setline(41);
      PyString.fromInterned("Classes to represent arbitrary sets (including sets of sets).\n\nThis module implements sets using dictionaries whose values are\nignored.  The usual operations (union, intersection, deletion, etc.)\nare provided as both methods and operators.\n\nImportant: sets are not sequences!  While they support 'x in s',\n'len(s)', and 'for x in s', none of those operations are unique for\nsequences; for example, mappings support all three as well.  The\ncharacteristic operation for sequences is subscripting with small\nintegers: s[i], for i in range(len(s)).  Sets don't support\nsubscripting at all.  Also, sequences allow multiple occurrences and\ntheir elements have a definite order; sets on the other hand don't\nrecord multiple occurrences and don't remember the order of element\ninsertion (which is why they don't support s[i]).\n\nThe following classes are provided:\n\nBaseSet -- All the operations common to both mutable and immutable\n    sets. This is an abstract class, not meant to be directly\n    instantiated.\n\nSet -- Mutable sets, subclass of BaseSet; not hashable.\n\nImmutableSet -- Immutable sets, subclass of BaseSet; hashable.\n    An iterable argument is mandatory to create an ImmutableSet.\n\n_TemporarilyImmutableSet -- A wrapper around a Set, hashable,\n    giving the same hash value as the immutable set equivalent\n    would have.  Do not use this class directly.\n\nOnly hashable objects can be added to a Set. In particular, you cannot\nreally add a Set as an element to another Set; if you try, what is\nactually added is an ImmutableSet built from it (it compares equal to\nthe one you tried adding).\n\nWhen you ask if `x in y' where x is a Set and y is a Set or\nImmutableSet, x is wrapped into a _TemporarilyImmutableSet z, and\nwhat's tested is actually `z in y'.\n\n");
      var1.setline(57);
      String[] var3 = new String[]{"ifilter", "ifilterfalse"};
      PyObject[] var5 = imp.importFrom("itertools", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ifilter", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("ifilterfalse", var4);
      var4 = null;
      var1.setline(59);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("BaseSet"), PyString.fromInterned("Set"), PyString.fromInterned("ImmutableSet")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(61);
      PyObject var7 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var7);
      var3 = null;
      var1.setline(62);
      PyObject var10000 = var1.getname("warnings").__getattr__("warn");
      var5 = new PyObject[]{PyString.fromInterned("the sets module is deprecated"), var1.getname("DeprecationWarning"), Py.newInteger(2)};
      String[] var8 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var8);
      var3 = null;
      var1.setline(65);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BaseSet", var5, BaseSet$1);
      var1.setlocal("BaseSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(378);
      var5 = new PyObject[]{var1.getname("BaseSet")};
      var4 = Py.makeClass("ImmutableSet", var5, ImmutableSet$28);
      var1.setlocal("ImmutableSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(403);
      var5 = new PyObject[]{var1.getname("BaseSet")};
      var4 = Py.makeClass("Set", var5, Set$33);
      var1.setlocal("Set", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(548);
      var5 = new PyObject[]{var1.getname("BaseSet")};
      var4 = Py.makeClass("_TemporarilyImmutableSet", var5, _TemporarilyImmutableSet$53);
      var1.setlocal("_TemporarilyImmutableSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseSet$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Common base class for mutable and immutable sets."));
      var1.setline(66);
      PyString.fromInterned("Common base class for mutable and immutable sets.");
      var1.setline(68);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("_data")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(72);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("This is an abstract class."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(81);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __len__$3, PyString.fromInterned("Return the number of elements of a set."));
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(85);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$4, PyString.fromInterned("Return string representation of a set.\n\n        This looks like 'Set([<list of elements>])'.\n        "));
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(93);
      PyObject var6 = var1.getname("__repr__");
      var1.setlocal("__str__", var6);
      var3 = null;
      var1.setline(95);
      var4 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, _repr$5, (PyObject)null);
      var1.setlocal("_repr", var5);
      var3 = null;
      var1.setline(101);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$6, PyString.fromInterned("Return an iterator over the elements or a set.\n\n        This is the keys iterator for the underlying dict.\n        "));
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(113);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __cmp__$7, (PyObject)null);
      var1.setlocal("__cmp__", var5);
      var3 = null;
      var1.setline(131);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$8, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(137);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$9, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(145);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy$10, PyString.fromInterned("Return a shallow copy of a set."));
      var1.setlocal("copy", var5);
      var3 = null;
      var1.setline(151);
      var6 = var1.getname("copy");
      var1.setlocal("__copy__", var6);
      var3 = null;
      var1.setline(153);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __deepcopy__$11, PyString.fromInterned("Return a deep copy of a set; used by copy module."));
      var1.setlocal("__deepcopy__", var5);
      var3 = null;
      var1.setline(178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __or__$12, PyString.fromInterned("Return the union of two sets as a new set.\n\n        (I.e. all elements that are in either set.)\n        "));
      var1.setlocal("__or__", var5);
      var3 = null;
      var1.setline(187);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, union$13, PyString.fromInterned("Return the union of two sets as a new set.\n\n        (I.e. all elements that are in either set.)\n        "));
      var1.setlocal("union", var5);
      var3 = null;
      var1.setline(196);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __and__$14, PyString.fromInterned("Return the intersection of two sets as a new set.\n\n        (I.e. all elements that are in both sets.)\n        "));
      var1.setlocal("__and__", var5);
      var3 = null;
      var1.setline(205);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, intersection$15, PyString.fromInterned("Return the intersection of two sets as a new set.\n\n        (I.e. all elements that are in both sets.)\n        "));
      var1.setlocal("intersection", var5);
      var3 = null;
      var1.setline(219);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __xor__$16, PyString.fromInterned("Return the symmetric difference of two sets as a new set.\n\n        (I.e. all elements that are in exactly one of the sets.)\n        "));
      var1.setlocal("__xor__", var5);
      var3 = null;
      var1.setline(228);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, symmetric_difference$17, PyString.fromInterned("Return the symmetric difference of two sets as a new set.\n\n        (I.e. all elements that are in exactly one of the sets.)\n        "));
      var1.setlocal("symmetric_difference", var5);
      var3 = null;
      var1.setline(247);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __sub__$18, PyString.fromInterned("Return the difference of two sets as a new Set.\n\n        (I.e. all elements that are in this set and not in the other.)\n        "));
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(256);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, difference$19, PyString.fromInterned("Return the difference of two sets as a new Set.\n\n        (I.e. all elements that are in this set and not in the other.)\n        "));
      var1.setlocal("difference", var5);
      var3 = null;
      var1.setline(274);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __contains__$20, PyString.fromInterned("Report whether an element is a member of a set.\n\n        (Called in response to the expression `element in self'.)\n        "));
      var1.setlocal("__contains__", var5);
      var3 = null;
      var1.setline(289);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, issubset$21, PyString.fromInterned("Report whether another set contains this set."));
      var1.setlocal("issubset", var5);
      var3 = null;
      var1.setline(298);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, issuperset$22, PyString.fromInterned("Report whether this set contains another set."));
      var1.setlocal("issuperset", var5);
      var3 = null;
      var1.setline(308);
      var6 = var1.getname("issubset");
      var1.setlocal("__le__", var6);
      var3 = null;
      var1.setline(309);
      var6 = var1.getname("issuperset");
      var1.setlocal("__ge__", var6);
      var3 = null;
      var1.setline(311);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$23, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(315);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __gt__$24, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(320);
      var6 = var1.getname("None");
      var1.setlocal("__hash__", var6);
      var3 = null;
      var1.setline(324);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _binary_sanity_check$25, (PyObject)null);
      var1.setlocal("_binary_sanity_check", var5);
      var3 = null;
      var1.setline(330);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _compute_hash$26, (PyObject)null);
      var1.setlocal("_compute_hash", var5);
      var3 = null;
      var1.setline(341);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _update$27, (PyObject)null);
      var1.setlocal("_update", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyString.fromInterned("This is an abstract class.");
      var1.setline(75);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._is(var1.getglobal("BaseSet"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("BaseSet is an abstract class.  Use Set or ImmutableSet."));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __len__$3(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Return the number of elements of a set.");
      var1.setline(83);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$4(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Return string representation of a set.\n\n        This looks like 'Set([<list of elements>])'.\n        ");
      var1.setline(90);
      PyObject var3 = var1.getlocal(0).__getattr__("_repr").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _repr$5(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("_data").__getattr__("keys").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(97);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(98);
         var1.getlocal(2).__getattr__("sort").__call__(var2);
      }

      var1.setline(99);
      var3 = PyString.fromInterned("%s(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Return an iterator over the elements or a set.\n\n        This is the keys iterator for the underlying dict.\n        ");
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("_data").__getattr__("iterkeys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$7(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("can't compare sets using cmp()"));
   }

   public PyObject __eq__$8(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__nonzero__()) {
         var1.setline(133);
         var3 = var1.getlocal(0).__getattr__("_data");
         PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("_data"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(135);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$9(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__nonzero__()) {
         var1.setline(139);
         var3 = var1.getlocal(0).__getattr__("_data");
         PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_data"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(141);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject copy$10(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyString.fromInterned("Return a shallow copy of a set.");
      var1.setline(147);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(148);
      var1.getlocal(1).__getattr__("_data").__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("_data"));
      var1.setline(149);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __deepcopy__$11(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyString.fromInterned("Return a deep copy of a set; used by copy module.");
      var1.setline(160);
      String[] var3 = new String[]{"deepcopy"};
      PyObject[] var6 = imp.importFrom("copy", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(161);
      PyObject var7 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(162);
      var7 = var1.getlocal(3);
      var1.getlocal(1).__setitem__(var1.getglobal("id").__call__(var2, var1.getlocal(0)), var7);
      var3 = null;
      var1.setline(163);
      var7 = var1.getlocal(3).__getattr__("_data");
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(164);
      var7 = var1.getglobal("True");
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(165);
      var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(165);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(167);
            var7 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(6, var4);
         var1.setline(166);
         PyObject var5 = var1.getlocal(5);
         var1.getlocal(4).__setitem__(var1.getlocal(2).__call__(var2, var1.getlocal(6), var1.getlocal(1)), var5);
         var5 = null;
      }
   }

   public PyObject __or__$12(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("Return the union of two sets as a new set.\n\n        (I.e. all elements that are in either set.)\n        ");
      var1.setline(183);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(184);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(185);
         var3 = var1.getlocal(0).__getattr__("union").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject union$13(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Return the union of two sets as a new set.\n\n        (I.e. all elements that are in either set.)\n        ");
      var1.setline(192);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(193);
      var1.getlocal(2).__getattr__("_update").__call__(var2, var1.getlocal(1));
      var1.setline(194);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __and__$14(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      PyString.fromInterned("Return the intersection of two sets as a new set.\n\n        (I.e. all elements that are in both sets.)\n        ");
      var1.setline(201);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(202);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(203);
         var3 = var1.getlocal(0).__getattr__("intersection").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject intersection$15(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyString.fromInterned("Return the intersection of two sets as a new set.\n\n        (I.e. all elements that are in both sets.)\n        ");
      var1.setline(210);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(211);
         var3 = var1.getglobal("Set").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(212);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._le(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(213);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(215);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0)});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(216);
      var3 = var1.getglobal("ifilter").__call__(var2, var1.getlocal(3).__getattr__("_data").__getattr__("__contains__"), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __xor__$16(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Return the symmetric difference of two sets as a new set.\n\n        (I.e. all elements that are in exactly one of the sets.)\n        ");
      var1.setline(224);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(225);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(226);
         var3 = var1.getlocal(0).__getattr__("symmetric_difference").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject symmetric_difference$17(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      PyString.fromInterned("Return the symmetric difference of two sets as a new set.\n\n        (I.e. all elements that are in exactly one of the sets.)\n        ");
      var1.setline(233);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(234);
      var3 = var1.getlocal(2).__getattr__("_data");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getglobal("True");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(0).__getattr__("_data");
      var1.setlocal(5, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(238);
         var3 = var1.getlocal(1).__getattr__("_data");
         var1.setlocal(6, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("AttributeError"))) {
            throw var7;
         }

         var1.setline(240);
         var4 = var1.getglobal("Set").__call__(var2, var1.getlocal(1)).__getattr__("_data");
         var1.setlocal(6, var4);
         var4 = null;
      }

      var1.setline(241);
      var3 = var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(6).__getattr__("__contains__"), var1.getlocal(5)).__iter__();

      while(true) {
         var1.setline(241);
         var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(243);
            var3 = var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(5).__getattr__("__contains__"), var1.getlocal(6)).__iter__();

            while(true) {
               var1.setline(243);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(245);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(7, var4);
               var1.setline(244);
               var5 = var1.getlocal(4);
               var1.getlocal(3).__setitem__(var1.getlocal(7), var5);
               var5 = null;
            }
         }

         var1.setlocal(7, var4);
         var1.setline(242);
         var5 = var1.getlocal(4);
         var1.getlocal(3).__setitem__(var1.getlocal(7), var5);
         var5 = null;
      }
   }

   public PyObject __sub__$18(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyString.fromInterned("Return the difference of two sets as a new Set.\n\n        (I.e. all elements that are in this set and not in the other.)\n        ");
      var1.setline(252);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(253);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(254);
         var3 = var1.getlocal(0).__getattr__("difference").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject difference$19(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Return the difference of two sets as a new Set.\n\n        (I.e. all elements that are in this set and not in the other.)\n        ");
      var1.setline(261);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getlocal(2).__getattr__("_data");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(264);
         var3 = var1.getlocal(1).__getattr__("_data");
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("AttributeError"))) {
            throw var7;
         }

         var1.setline(266);
         var4 = var1.getglobal("Set").__call__(var2, var1.getlocal(1)).__getattr__("_data");
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(267);
      var3 = var1.getglobal("True");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(268);
      var3 = var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(4).__getattr__("__contains__"), var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(268);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(270);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var4);
         var1.setline(269);
         PyObject var5 = var1.getlocal(5);
         var1.getlocal(3).__setitem__(var1.getlocal(6), var5);
         var5 = null;
      }
   }

   public PyObject __contains__$20(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyString.fromInterned("Report whether an element is a member of a set.\n\n        (Called in response to the expression `element in self'.)\n        ");

      PyObject var10000;
      PyObject var3;
      try {
         var1.setline(280);
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_data"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("TypeError"))) {
            var1.setline(282);
            PyObject var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__as_temporarily_immutable__"), (PyObject)var1.getglobal("None"));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(283);
            var5 = var1.getlocal(2);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(284);
               throw Py.makeException();
            } else {
               var1.setline(285);
               var5 = var1.getlocal(2).__call__(var2);
               var10000 = var5._in(var1.getlocal(0).__getattr__("_data"));
               var5 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject issubset$21(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("Report whether another set contains this set.");
      var1.setline(291);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(292);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(293);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(294);
         PyObject var4 = var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(1).__getattr__("_data").__getattr__("__contains__"), var1.getlocal(0)).__iter__();
         var1.setline(294);
         PyObject var5 = var4.__iternext__();
         if (var5 == null) {
            var1.setline(296);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setlocal(2, var5);
            var1.setline(295);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject issuperset$22(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      PyString.fromInterned("Report whether this set contains another set.");
      var1.setline(300);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(301);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(302);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(303);
         PyObject var4 = var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(0).__getattr__("_data").__getattr__("__contains__"), var1.getlocal(1)).__iter__();
         var1.setline(303);
         PyObject var5 = var4.__iternext__();
         if (var5 == null) {
            var1.setline(305);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setlocal(2, var5);
            var1.setline(304);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __lt__$23(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(313);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("issubset").__call__(var2, var1.getlocal(1));
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __gt__$24(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(317);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("issuperset").__call__(var2, var1.getlocal(1));
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _binary_sanity_check$25(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(328);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("Binary operation only permitted between sets"));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _compute_hash$26(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(337);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(337);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(339);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(338);
         PyObject var5 = var1.getlocal(1);
         var5 = var5._ixor(var1.getglobal("hash").__call__(var2, var1.getlocal(2)));
         var1.setlocal(1, var5);
      }
   }

   public PyObject _update$27(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyObject var3 = var1.getlocal(0).__getattr__("_data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(346);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__nonzero__()) {
         var1.setline(347);
         var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(1).__getattr__("_data"));
         var1.setline(348);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(350);
         var3 = var1.getglobal("True");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(352);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple"), var1.getglobal("xrange")}));
         var3 = null;
         PyObject var4;
         PyObject var5;
         if (!var10000.__nonzero__()) {
            var1.setline(368);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(368);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(5, var4);

               try {
                  var1.setline(370);
                  var5 = var1.getlocal(3);
                  var1.getlocal(2).__setitem__(var1.getlocal(5), var5);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (!var10.match(var1.getglobal("TypeError"))) {
                     throw var10;
                  }

                  var1.setline(372);
                  PyObject var6 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("__as_immutable__"), (PyObject)var1.getglobal("None"));
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(373);
                  var6 = var1.getlocal(6);
                  var10000 = var6._is(var1.getglobal("None"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(374);
                     throw Py.makeException();
                  }

                  var1.setline(375);
                  var6 = var1.getlocal(3);
                  var1.getlocal(2).__setitem__(var1.getlocal(6).__call__(var2), var6);
                  var6 = null;
               }
            }
         } else {
            var1.setline(355);
            var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;

            while(true) {
               var1.setline(356);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               try {
                  var1.setline(358);
                  var3 = var1.getlocal(4).__iter__();

                  while(true) {
                     var1.setline(358);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(360);
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(359);
                     var5 = var1.getlocal(3);
                     var1.getlocal(2).__setitem__(var1.getlocal(5), var5);
                     var5 = null;
                  }
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (!var9.match(var1.getglobal("TypeError"))) {
                     throw var9;
                  }

                  var1.setline(362);
                  var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("__as_immutable__"), (PyObject)var1.getglobal("None"));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(363);
                  var4 = var1.getlocal(6);
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(364);
                     throw Py.makeException();
                  }

                  var1.setline(365);
                  var4 = var1.getlocal(3);
                  var1.getlocal(2).__setitem__(var1.getlocal(6).__call__(var2), var4);
                  var4 = null;
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject ImmutableSet$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Immutable set class."));
      var1.setline(379);
      PyString.fromInterned("Immutable set class.");
      var1.setline(381);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("_hashcode")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(385);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$29, PyString.fromInterned("Construct an immutable set from an optional iterable."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(392);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$30, (PyObject)null);
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(397);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$31, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(400);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$32, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyString.fromInterned("Construct an immutable set from an optional iterable.");
      var1.setline(387);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_hashcode", var3);
      var3 = null;
      var1.setline(388);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_data", var4);
      var3 = null;
      var1.setline(389);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(390);
         var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __hash__$30(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("_hashcode");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(394);
         var3 = var1.getlocal(0).__getattr__("_compute_hash").__call__(var2);
         var1.getlocal(0).__setattr__("_hashcode", var3);
         var3 = null;
      }

      var1.setline(395);
      var3 = var1.getlocal(0).__getattr__("_hashcode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getstate__$31(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_data"), var1.getlocal(0).__getattr__("_hashcode")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$32(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("_data", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_hashcode", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Set$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Mutable set class."));
      var1.setline(404);
      PyString.fromInterned(" Mutable set class.");
      var1.setline(406);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(410);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$34, PyString.fromInterned("Construct a set from an optional iterable."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(416);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$35, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(420);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$36, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      var1.setline(428);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ior__$37, PyString.fromInterned("Update a set with the union of itself and another."));
      var1.setlocal("__ior__", var5);
      var3 = null;
      var1.setline(434);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, union_update$38, PyString.fromInterned("Update a set with the union of itself and another."));
      var1.setlocal("union_update", var5);
      var3 = null;
      var1.setline(438);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iand__$39, PyString.fromInterned("Update a set with the intersection of itself and another."));
      var1.setlocal("__iand__", var5);
      var3 = null;
      var1.setline(444);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, intersection_update$40, PyString.fromInterned("Update a set with the intersection of itself and another."));
      var1.setlocal("intersection_update", var5);
      var3 = null;
      var1.setline(451);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ixor__$41, PyString.fromInterned("Update a set with the symmetric difference of itself and another."));
      var1.setlocal("__ixor__", var5);
      var3 = null;
      var1.setline(457);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, symmetric_difference_update$42, PyString.fromInterned("Update a set with the symmetric difference of itself and another."));
      var1.setlocal("symmetric_difference_update", var5);
      var3 = null;
      var1.setline(471);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __isub__$43, PyString.fromInterned("Remove all elements of another set from this set."));
      var1.setlocal("__isub__", var5);
      var3 = null;
      var1.setline(477);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, difference_update$44, PyString.fromInterned("Remove all elements of another set from this set."));
      var1.setlocal("difference_update", var5);
      var3 = null;
      var1.setline(489);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, update$45, PyString.fromInterned("Add all values from an iterable (such as a list or file)."));
      var1.setlocal("update", var5);
      var3 = null;
      var1.setline(493);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, clear$46, PyString.fromInterned("Remove all elements from this set."));
      var1.setlocal("clear", var5);
      var3 = null;
      var1.setline(499);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add$47, PyString.fromInterned("Add an element to a set.\n\n        This has no effect if the element is already present.\n        "));
      var1.setlocal("add", var5);
      var3 = null;
      var1.setline(512);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, remove$48, PyString.fromInterned("Remove an element from a set; it must be a member.\n\n        If the element is not a member, raise a KeyError.\n        "));
      var1.setlocal("remove", var5);
      var3 = null;
      var1.setline(525);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, discard$49, PyString.fromInterned("Remove an element from a set if it is a member.\n\n        If the element is not a member, do nothing.\n        "));
      var1.setlocal("discard", var5);
      var3 = null;
      var1.setline(535);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, pop$50, PyString.fromInterned("Remove and return an arbitrary set element."));
      var1.setlocal("pop", var5);
      var3 = null;
      var1.setline(539);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __as_immutable__$51, (PyObject)null);
      var1.setlocal("__as_immutable__", var5);
      var3 = null;
      var1.setline(543);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __as_temporarily_immutable__$52, (PyObject)null);
      var1.setlocal("__as_temporarily_immutable__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyString.fromInterned("Construct a set from an optional iterable.");
      var1.setline(412);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_data", var3);
      var3 = null;
      var1.setline(413);
      PyObject var4 = var1.getlocal(1);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(414);
         var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$35(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_data")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$36(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 1);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("_data", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __ior__$37(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyString.fromInterned("Update a set with the union of itself and another.");
      var1.setline(430);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(431);
      var1.getlocal(0).__getattr__("_data").__getattr__("update").__call__(var2, var1.getlocal(1).__getattr__("_data"));
      var1.setline(432);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject union_update$38(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyString.fromInterned("Update a set with the union of itself and another.");
      var1.setline(436);
      var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iand__$39(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyString.fromInterned("Update a set with the intersection of itself and another.");
      var1.setline(440);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(441);
      PyObject var3 = var1.getlocal(0)._and(var1.getlocal(1)).__getattr__("_data");
      var1.getlocal(0).__setattr__("_data", var3);
      var3 = null;
      var1.setline(442);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject intersection_update$40(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyString.fromInterned("Update a set with the intersection of itself and another.");
      var1.setline(446);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__nonzero__()) {
         var1.setline(447);
         var3 = var1.getlocal(0);
         var3 = var3._iand(var1.getlocal(1));
         var1.setlocal(0, var3);
      } else {
         var1.setline(449);
         var3 = var1.getlocal(0).__getattr__("intersection").__call__(var2, var1.getlocal(1)).__getattr__("_data");
         var1.getlocal(0).__setattr__("_data", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __ixor__$41(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyString.fromInterned("Update a set with the symmetric difference of itself and another.");
      var1.setline(453);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(454);
      var1.getlocal(0).__getattr__("symmetric_difference_update").__call__(var2, var1.getlocal(1));
      var1.setline(455);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject symmetric_difference_update$42(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyString.fromInterned("Update a set with the symmetric difference of itself and another.");
      var1.setline(459);
      PyObject var3 = var1.getlocal(0).__getattr__("_data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(460);
      var3 = var1.getglobal("True");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(461);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(462);
         var3 = var1.getglobal("Set").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(463);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         var1.getlocal(0).__getattr__("clear").__call__(var2);
      }

      var1.setline(465);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(465);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(466);
         PyObject var5 = var1.getlocal(4);
         var10000 = var5._in(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(467);
            var1.getlocal(2).__delitem__(var1.getlocal(4));
         } else {
            var1.setline(469);
            var5 = var1.getlocal(3);
            var1.getlocal(2).__setitem__(var1.getlocal(4), var5);
            var5 = null;
         }
      }
   }

   public PyObject __isub__$43(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString.fromInterned("Remove all elements of another set from this set.");
      var1.setline(473);
      var1.getlocal(0).__getattr__("_binary_sanity_check").__call__(var2, var1.getlocal(1));
      var1.setline(474);
      var1.getlocal(0).__getattr__("difference_update").__call__(var2, var1.getlocal(1));
      var1.setline(475);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject difference_update$44(PyFrame var1, ThreadState var2) {
      var1.setline(478);
      PyString.fromInterned("Remove all elements of another set from this set.");
      var1.setline(479);
      PyObject var3 = var1.getlocal(0).__getattr__("_data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(480);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BaseSet")).__not__().__nonzero__()) {
         var1.setline(481);
         var3 = var1.getglobal("Set").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(482);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(483);
         var1.getlocal(0).__getattr__("clear").__call__(var2);
      }

      var1.setline(484);
      var3 = var1.getglobal("ifilter").__call__(var2, var1.getlocal(2).__getattr__("__contains__"), var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(484);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(485);
         var1.getlocal(2).__delitem__(var1.getlocal(3));
      }
   }

   public PyObject update$45(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyString.fromInterned("Add all values from an iterable (such as a list or file).");
      var1.setline(491);
      var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear$46(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyString.fromInterned("Remove all elements from this set.");
      var1.setline(495);
      var1.getlocal(0).__getattr__("_data").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$47(PyFrame var1, ThreadState var2) {
      var1.setline(503);
      PyString.fromInterned("Add an element to a set.\n\n        This has no effect if the element is already present.\n        ");

      PyException var3;
      try {
         var1.setline(505);
         PyObject var6 = var1.getglobal("True");
         var1.getlocal(0).__getattr__("_data").__setitem__(var1.getlocal(1), var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("TypeError"))) {
            throw var3;
         }

         var1.setline(507);
         PyObject var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__as_immutable__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(508);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(509);
            throw Py.makeException();
         }

         var1.setline(510);
         var4 = var1.getglobal("True");
         var1.getlocal(0).__getattr__("_data").__setitem__(var1.getlocal(2).__call__(var2), var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject remove$48(PyFrame var1, ThreadState var2) {
      var1.setline(516);
      PyString.fromInterned("Remove an element from a set; it must be a member.\n\n        If the element is not a member, raise a KeyError.\n        ");

      try {
         var1.setline(518);
         var1.getlocal(0).__getattr__("_data").__delitem__(var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("TypeError"))) {
            throw var3;
         }

         var1.setline(520);
         PyObject var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__as_temporarily_immutable__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(521);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(522);
            throw Py.makeException();
         }

         var1.setline(523);
         var1.getlocal(0).__getattr__("_data").__delitem__(var1.getlocal(2).__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject discard$49(PyFrame var1, ThreadState var2) {
      var1.setline(529);
      PyString.fromInterned("Remove an element from a set if it is a member.\n\n        If the element is not a member, do nothing.\n        ");

      try {
         var1.setline(531);
         var1.getlocal(0).__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(533);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop$50(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyString.fromInterned("Remove and return an arbitrary set element.");
      var1.setline(537);
      PyObject var3 = var1.getlocal(0).__getattr__("_data").__getattr__("popitem").__call__(var2).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __as_immutable__$51(PyFrame var1, ThreadState var2) {
      var1.setline(541);
      PyObject var3 = var1.getglobal("ImmutableSet").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __as_temporarily_immutable__$52(PyFrame var1, ThreadState var2) {
      var1.setline(545);
      PyObject var3 = var1.getglobal("_TemporarilyImmutableSet").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _TemporarilyImmutableSet$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(552);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$54, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(556);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$55, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$54(PyFrame var1, ThreadState var2) {
      var1.setline(553);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_set", var3);
      var3 = null;
      var1.setline(554);
      var3 = var1.getlocal(1).__getattr__("_data");
      var1.getlocal(0).__setattr__("_data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __hash__$55(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      PyObject var3 = var1.getlocal(0).__getattr__("_set").__getattr__("_compute_hash").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public sets$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BaseSet$1 = Py.newCode(0, var2, var1, "BaseSet", 65, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 72, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$3 = Py.newCode(1, var2, var1, "__len__", 81, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$4 = Py.newCode(1, var2, var1, "__repr__", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sorted", "elements"};
      _repr$5 = Py.newCode(2, var2, var1, "_repr", 95, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$6 = Py.newCode(1, var2, var1, "__iter__", 101, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$7 = Py.newCode(2, var2, var1, "__cmp__", 113, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$8 = Py.newCode(2, var2, var1, "__eq__", 131, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$9 = Py.newCode(2, var2, var1, "__ne__", 137, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      copy$10 = Py.newCode(1, var2, var1, "copy", 145, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "memo", "deepcopy", "result", "data", "value", "elt"};
      __deepcopy__$11 = Py.newCode(2, var2, var1, "__deepcopy__", 153, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __or__$12 = Py.newCode(2, var2, var1, "__or__", 178, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result"};
      union$13 = Py.newCode(2, var2, var1, "union", 187, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __and__$14 = Py.newCode(2, var2, var1, "__and__", 196, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "little", "big", "common"};
      intersection$15 = Py.newCode(2, var2, var1, "intersection", 205, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __xor__$16 = Py.newCode(2, var2, var1, "__xor__", 219, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "data", "value", "selfdata", "otherdata", "elt"};
      symmetric_difference$17 = Py.newCode(2, var2, var1, "symmetric_difference", 228, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __sub__$18 = Py.newCode(2, var2, var1, "__sub__", 247, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "data", "otherdata", "value", "elt"};
      difference$19 = Py.newCode(2, var2, var1, "difference", 256, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "transform"};
      __contains__$20 = Py.newCode(2, var2, var1, "__contains__", 274, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "elt"};
      issubset$21 = Py.newCode(2, var2, var1, "issubset", 289, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "elt"};
      issuperset$22 = Py.newCode(2, var2, var1, "issuperset", 298, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$23 = Py.newCode(2, var2, var1, "__lt__", 311, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __gt__$24 = Py.newCode(2, var2, var1, "__gt__", 315, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      _binary_sanity_check$25 = Py.newCode(2, var2, var1, "_binary_sanity_check", 324, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "elt"};
      _compute_hash$26 = Py.newCode(1, var2, var1, "_compute_hash", 330, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "data", "value", "it", "element", "transform"};
      _update$27 = Py.newCode(2, var2, var1, "_update", 341, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ImmutableSet$28 = Py.newCode(0, var2, var1, "ImmutableSet", 378, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "iterable"};
      __init__$29 = Py.newCode(2, var2, var1, "__init__", 385, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$30 = Py.newCode(1, var2, var1, "__hash__", 392, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$31 = Py.newCode(1, var2, var1, "__getstate__", 397, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$32 = Py.newCode(2, var2, var1, "__setstate__", 400, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Set$33 = Py.newCode(0, var2, var1, "Set", 403, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "iterable"};
      __init__$34 = Py.newCode(2, var2, var1, "__init__", 410, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$35 = Py.newCode(1, var2, var1, "__getstate__", 416, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      __setstate__$36 = Py.newCode(2, var2, var1, "__setstate__", 420, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ior__$37 = Py.newCode(2, var2, var1, "__ior__", 428, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      union_update$38 = Py.newCode(2, var2, var1, "union_update", 434, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __iand__$39 = Py.newCode(2, var2, var1, "__iand__", 438, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      intersection_update$40 = Py.newCode(2, var2, var1, "intersection_update", 444, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ixor__$41 = Py.newCode(2, var2, var1, "__ixor__", 451, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "data", "value", "elt"};
      symmetric_difference_update$42 = Py.newCode(2, var2, var1, "symmetric_difference_update", 457, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __isub__$43 = Py.newCode(2, var2, var1, "__isub__", 471, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "data", "elt"};
      difference_update$44 = Py.newCode(2, var2, var1, "difference_update", 477, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable"};
      update$45 = Py.newCode(2, var2, var1, "update", 489, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$46 = Py.newCode(1, var2, var1, "clear", 493, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "transform"};
      add$47 = Py.newCode(2, var2, var1, "add", 499, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "transform"};
      remove$48 = Py.newCode(2, var2, var1, "remove", 512, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      discard$49 = Py.newCode(2, var2, var1, "discard", 525, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop$50 = Py.newCode(1, var2, var1, "pop", 535, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __as_immutable__$51 = Py.newCode(1, var2, var1, "__as_immutable__", 539, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __as_temporarily_immutable__$52 = Py.newCode(1, var2, var1, "__as_temporarily_immutable__", 543, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _TemporarilyImmutableSet$53 = Py.newCode(0, var2, var1, "_TemporarilyImmutableSet", 548, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "set"};
      __init__$54 = Py.newCode(2, var2, var1, "__init__", 552, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$55 = Py.newCode(1, var2, var1, "__hash__", 556, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sets$py("sets$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sets$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BaseSet$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__len__$3(var2, var3);
         case 4:
            return this.__repr__$4(var2, var3);
         case 5:
            return this._repr$5(var2, var3);
         case 6:
            return this.__iter__$6(var2, var3);
         case 7:
            return this.__cmp__$7(var2, var3);
         case 8:
            return this.__eq__$8(var2, var3);
         case 9:
            return this.__ne__$9(var2, var3);
         case 10:
            return this.copy$10(var2, var3);
         case 11:
            return this.__deepcopy__$11(var2, var3);
         case 12:
            return this.__or__$12(var2, var3);
         case 13:
            return this.union$13(var2, var3);
         case 14:
            return this.__and__$14(var2, var3);
         case 15:
            return this.intersection$15(var2, var3);
         case 16:
            return this.__xor__$16(var2, var3);
         case 17:
            return this.symmetric_difference$17(var2, var3);
         case 18:
            return this.__sub__$18(var2, var3);
         case 19:
            return this.difference$19(var2, var3);
         case 20:
            return this.__contains__$20(var2, var3);
         case 21:
            return this.issubset$21(var2, var3);
         case 22:
            return this.issuperset$22(var2, var3);
         case 23:
            return this.__lt__$23(var2, var3);
         case 24:
            return this.__gt__$24(var2, var3);
         case 25:
            return this._binary_sanity_check$25(var2, var3);
         case 26:
            return this._compute_hash$26(var2, var3);
         case 27:
            return this._update$27(var2, var3);
         case 28:
            return this.ImmutableSet$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.__hash__$30(var2, var3);
         case 31:
            return this.__getstate__$31(var2, var3);
         case 32:
            return this.__setstate__$32(var2, var3);
         case 33:
            return this.Set$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.__getstate__$35(var2, var3);
         case 36:
            return this.__setstate__$36(var2, var3);
         case 37:
            return this.__ior__$37(var2, var3);
         case 38:
            return this.union_update$38(var2, var3);
         case 39:
            return this.__iand__$39(var2, var3);
         case 40:
            return this.intersection_update$40(var2, var3);
         case 41:
            return this.__ixor__$41(var2, var3);
         case 42:
            return this.symmetric_difference_update$42(var2, var3);
         case 43:
            return this.__isub__$43(var2, var3);
         case 44:
            return this.difference_update$44(var2, var3);
         case 45:
            return this.update$45(var2, var3);
         case 46:
            return this.clear$46(var2, var3);
         case 47:
            return this.add$47(var2, var3);
         case 48:
            return this.remove$48(var2, var3);
         case 49:
            return this.discard$49(var2, var3);
         case 50:
            return this.pop$50(var2, var3);
         case 51:
            return this.__as_immutable__$51(var2, var3);
         case 52:
            return this.__as_temporarily_immutable__$52(var2, var3);
         case 53:
            return this._TemporarilyImmutableSet$53(var2, var3);
         case 54:
            return this.__init__$54(var2, var3);
         case 55:
            return this.__hash__$55(var2, var3);
         default:
            return null;
      }
   }
}
