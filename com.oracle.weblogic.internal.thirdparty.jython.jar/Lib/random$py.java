import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("random.py")
public class random$py extends PyFunctionTable implements PyRunnable {
   static random$py self;
   static final PyCode f$0;
   static final PyCode Random$1;
   static final PyCode __init__$2;
   static final PyCode seed$3;
   static final PyCode getstate$4;
   static final PyCode setstate$5;
   static final PyCode f$6;
   static final PyCode jumpahead$7;
   static final PyCode __getstate__$8;
   static final PyCode __setstate__$9;
   static final PyCode __reduce__$10;
   static final PyCode randrange$11;
   static final PyCode randint$12;
   static final PyCode _randbelow$13;
   static final PyCode choice$14;
   static final PyCode shuffle$15;
   static final PyCode sample$16;
   static final PyCode uniform$17;
   static final PyCode triangular$18;
   static final PyCode normalvariate$19;
   static final PyCode lognormvariate$20;
   static final PyCode expovariate$21;
   static final PyCode vonmisesvariate$22;
   static final PyCode gammavariate$23;
   static final PyCode gauss$24;
   static final PyCode betavariate$25;
   static final PyCode paretovariate$26;
   static final PyCode weibullvariate$27;
   static final PyCode WichmannHill$28;
   static final PyCode seed$29;
   static final PyCode random$30;
   static final PyCode getstate$31;
   static final PyCode setstate$32;
   static final PyCode jumpahead$33;
   static final PyCode _WichmannHill__whseed$34;
   static final PyCode whseed$35;
   static final PyCode SystemRandom$36;
   static final PyCode random$37;
   static final PyCode getrandbits$38;
   static final PyCode _stub$39;
   static final PyCode _notimplemented$40;
   static final PyCode _test_generator$41;
   static final PyCode _test$42;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Random variable generators.\n\n    integers\n    --------\n           uniform within range\n\n    sequences\n    ---------\n           pick random element\n           pick random sample\n           generate random permutation\n\n    distributions on the real line:\n    ------------------------------\n           uniform\n           triangular\n           normal (Gaussian)\n           lognormal\n           negative exponential\n           gamma\n           beta\n           pareto\n           Weibull\n\n    distributions on the circle (angles 0 to 2pi)\n    ---------------------------------------------\n           circular uniform\n           von Mises\n\nGeneral notes on the underlying Mersenne Twister core generator:\n\n* The period is 2**19937-1.\n* It is one of the most extensively tested generators in existence.\n* Without a direct way to compute N steps forward, the semantics of\n  jumpahead(n) are weakened to simply jump to another distant state and rely\n  on the large period to avoid overlapping sequences.\n* The random() method is implemented in C, executes in a single Python step,\n  and is, therefore, threadsafe.\n\n"));
      var1.setline(40);
      PyString.fromInterned("Random variable generators.\n\n    integers\n    --------\n           uniform within range\n\n    sequences\n    ---------\n           pick random element\n           pick random sample\n           generate random permutation\n\n    distributions on the real line:\n    ------------------------------\n           uniform\n           triangular\n           normal (Gaussian)\n           lognormal\n           negative exponential\n           gamma\n           beta\n           pareto\n           Weibull\n\n    distributions on the circle (angles 0 to 2pi)\n    ---------------------------------------------\n           circular uniform\n           von Mises\n\nGeneral notes on the underlying Mersenne Twister core generator:\n\n* The period is 2**19937-1.\n* It is one of the most extensively tested generators in existence.\n* Without a direct way to compute N steps forward, the semantics of\n  jumpahead(n) are weakened to simply jump to another distant state and rely\n  on the large period to avoid overlapping sequences.\n* The random() method is implemented in C, executes in a single Python step,\n  and is, therefore, threadsafe.\n\n");
      var1.setline(42);
      String[] var3 = new String[]{"division"};
      PyObject[] var5 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("division", var4);
      var4 = null;
      var1.setline(43);
      var3 = new String[]{"warn"};
      var5 = imp.importFrom("warnings", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_warn", var4);
      var4 = null;
      var1.setline(44);
      var3 = new String[]{"MethodType", "BuiltinMethodType"};
      var5 = imp.importFrom("types", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_MethodType", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("_BuiltinMethodType", var4);
      var4 = null;
      var1.setline(45);
      var3 = new String[]{"log", "exp", "pi", "e", "ceil"};
      var5 = imp.importFrom("math", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_log", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("_exp", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("_pi", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("_e", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("_ceil", var4);
      var4 = null;
      var1.setline(46);
      var3 = new String[]{"sqrt", "acos", "cos", "sin"};
      var5 = imp.importFrom("math", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_sqrt", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("_acos", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("_cos", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("_sin", var4);
      var4 = null;
      var1.setline(47);
      var3 = new String[]{"urandom"};
      var5 = imp.importFrom("os", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_urandom", var4);
      var4 = null;
      var1.setline(48);
      var3 = new String[]{"hexlify"};
      var5 = imp.importFrom("binascii", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_hexlify", var4);
      var4 = null;
      var1.setline(49);
      PyObject var6 = imp.importOneAs("hashlib", var1, -1);
      var1.setlocal("_hashlib", var6);
      var3 = null;
      var1.setline(51);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Random"), PyString.fromInterned("seed"), PyString.fromInterned("random"), PyString.fromInterned("uniform"), PyString.fromInterned("randint"), PyString.fromInterned("choice"), PyString.fromInterned("sample"), PyString.fromInterned("randrange"), PyString.fromInterned("shuffle"), PyString.fromInterned("normalvariate"), PyString.fromInterned("lognormvariate"), PyString.fromInterned("expovariate"), PyString.fromInterned("vonmisesvariate"), PyString.fromInterned("gammavariate"), PyString.fromInterned("triangular"), PyString.fromInterned("gauss"), PyString.fromInterned("betavariate"), PyString.fromInterned("paretovariate"), PyString.fromInterned("weibullvariate"), PyString.fromInterned("getstate"), PyString.fromInterned("setstate"), PyString.fromInterned("jumpahead"), PyString.fromInterned("WichmannHill"), PyString.fromInterned("getrandbits"), PyString.fromInterned("SystemRandom")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(58);
      var6 = Py.newInteger(4)._mul(var1.getname("_exp").__call__((ThreadState)var2, (PyObject)Py.newFloat(-0.5)))._truediv(var1.getname("_sqrt").__call__((ThreadState)var2, (PyObject)Py.newFloat(2.0)));
      var1.setlocal("NV_MAGICCONST", var6);
      var3 = null;
      var1.setline(59);
      var6 = Py.newFloat(2.0)._mul(var1.getname("_pi"));
      var1.setlocal("TWOPI", var6);
      var3 = null;
      var1.setline(60);
      var6 = var1.getname("_log").__call__((ThreadState)var2, (PyObject)Py.newFloat(4.0));
      var1.setlocal("LOG4", var6);
      var3 = null;
      var1.setline(61);
      var6 = Py.newFloat(1.0)._add(var1.getname("_log").__call__((ThreadState)var2, (PyObject)Py.newFloat(4.5)));
      var1.setlocal("SG_MAGICCONST", var6);
      var3 = null;
      var1.setline(62);
      PyInteger var8 = Py.newInteger(53);
      var1.setlocal("BPF", var8);
      var3 = null;
      var1.setline(63);
      var6 = Py.newInteger(2)._pow(var1.getname("BPF").__neg__());
      var1.setlocal("RECIP_BPF", var6);
      var3 = null;
      var1.setline(70);
      var6 = imp.importOne("_random", var1, -1);
      var1.setlocal("_random", var6);
      var3 = null;
      var1.setline(72);
      var5 = new PyObject[]{var1.getname("_random").__getattr__("Random")};
      var4 = Py.makeClass("Random", var5, Random$1);
      var1.setlocal("Random", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(649);
      var5 = new PyObject[]{var1.getname("Random")};
      var4 = Py.makeClass("WichmannHill", var5, WichmannHill$28);
      var1.setlocal("WichmannHill", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(799);
      var5 = new PyObject[]{var1.getname("Random")};
      var4 = Py.makeClass("SystemRandom", var5, SystemRandom$36);
      var1.setlocal("SystemRandom", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(833);
      var5 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var5, _test_generator$41, (PyObject)null);
      var1.setlocal("_test_generator", var9);
      var3 = null;
      var1.setline(855);
      var5 = new PyObject[]{Py.newInteger(2000)};
      var9 = new PyFunction(var1.f_globals, var5, _test$42, (PyObject)null);
      var1.setlocal("_test", var9);
      var3 = null;
      var1.setline(879);
      var6 = var1.getname("Random").__call__(var2);
      var1.setlocal("_inst", var6);
      var3 = null;
      var1.setline(880);
      var6 = var1.getname("_inst").__getattr__("seed");
      var1.setlocal("seed", var6);
      var3 = null;
      var1.setline(881);
      var6 = var1.getname("_inst").__getattr__("random");
      var1.setlocal("random", var6);
      var3 = null;
      var1.setline(882);
      var6 = var1.getname("_inst").__getattr__("uniform");
      var1.setlocal("uniform", var6);
      var3 = null;
      var1.setline(883);
      var6 = var1.getname("_inst").__getattr__("triangular");
      var1.setlocal("triangular", var6);
      var3 = null;
      var1.setline(884);
      var6 = var1.getname("_inst").__getattr__("randint");
      var1.setlocal("randint", var6);
      var3 = null;
      var1.setline(885);
      var6 = var1.getname("_inst").__getattr__("choice");
      var1.setlocal("choice", var6);
      var3 = null;
      var1.setline(886);
      var6 = var1.getname("_inst").__getattr__("randrange");
      var1.setlocal("randrange", var6);
      var3 = null;
      var1.setline(887);
      var6 = var1.getname("_inst").__getattr__("sample");
      var1.setlocal("sample", var6);
      var3 = null;
      var1.setline(888);
      var6 = var1.getname("_inst").__getattr__("shuffle");
      var1.setlocal("shuffle", var6);
      var3 = null;
      var1.setline(889);
      var6 = var1.getname("_inst").__getattr__("normalvariate");
      var1.setlocal("normalvariate", var6);
      var3 = null;
      var1.setline(890);
      var6 = var1.getname("_inst").__getattr__("lognormvariate");
      var1.setlocal("lognormvariate", var6);
      var3 = null;
      var1.setline(891);
      var6 = var1.getname("_inst").__getattr__("expovariate");
      var1.setlocal("expovariate", var6);
      var3 = null;
      var1.setline(892);
      var6 = var1.getname("_inst").__getattr__("vonmisesvariate");
      var1.setlocal("vonmisesvariate", var6);
      var3 = null;
      var1.setline(893);
      var6 = var1.getname("_inst").__getattr__("gammavariate");
      var1.setlocal("gammavariate", var6);
      var3 = null;
      var1.setline(894);
      var6 = var1.getname("_inst").__getattr__("gauss");
      var1.setlocal("gauss", var6);
      var3 = null;
      var1.setline(895);
      var6 = var1.getname("_inst").__getattr__("betavariate");
      var1.setlocal("betavariate", var6);
      var3 = null;
      var1.setline(896);
      var6 = var1.getname("_inst").__getattr__("paretovariate");
      var1.setlocal("paretovariate", var6);
      var3 = null;
      var1.setline(897);
      var6 = var1.getname("_inst").__getattr__("weibullvariate");
      var1.setlocal("weibullvariate", var6);
      var3 = null;
      var1.setline(898);
      var6 = var1.getname("_inst").__getattr__("getstate");
      var1.setlocal("getstate", var6);
      var3 = null;
      var1.setline(899);
      var6 = var1.getname("_inst").__getattr__("setstate");
      var1.setlocal("setstate", var6);
      var3 = null;
      var1.setline(900);
      var6 = var1.getname("_inst").__getattr__("jumpahead");
      var1.setlocal("jumpahead", var6);
      var3 = null;
      var1.setline(901);
      var6 = var1.getname("_inst").__getattr__("getrandbits");
      var1.setlocal("getrandbits", var6);
      var3 = null;
      var1.setline(903);
      var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(904);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Random$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Random number generator base class used by bound module functions.\n\n    Used to instantiate instances of Random to get generators that don't\n    share state.  Especially useful for multi-threaded programs, creating\n    a different instance of Random for each thread, and using the jumpahead()\n    method to ensure that the generated sequences seen by each thread don't\n    overlap.\n\n    Class Random can also be subclassed if you want to use a different basic\n    generator of your own devising: in that case, override the following\n    methods: random(), seed(), getstate(), setstate() and jumpahead().\n    Optionally, implement a getrandbits() method so that randrange() can cover\n    arbitrarily large ranges.\n\n    "));
      var1.setline(87);
      PyString.fromInterned("Random number generator base class used by bound module functions.\n\n    Used to instantiate instances of Random to get generators that don't\n    share state.  Especially useful for multi-threaded programs, creating\n    a different instance of Random for each thread, and using the jumpahead()\n    method to ensure that the generated sequences seen by each thread don't\n    overlap.\n\n    Class Random can also be subclassed if you want to use a different basic\n    generator of your own devising: in that case, override the following\n    methods: random(), seed(), getstate(), setstate() and jumpahead().\n    Optionally, implement a getrandbits() method so that randrange() can cover\n    arbitrarily large ranges.\n\n    ");
      var1.setline(89);
      PyInteger var3 = Py.newInteger(3);
      var1.setlocal("VERSION", var3);
      var3 = null;
      var1.setline(91);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("Initialize an instance.\n\n        Optional argument x controls seeding, as for Random.seed().\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(100);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, seed$3, PyString.fromInterned("Initialize internal state from hashable object.\n\n        None or no argument seeds from current time or from an operating\n        system specific randomness source if available.\n\n        If a is not None or an int or long, hash(a) is used instead.\n        "));
      var1.setlocal("seed", var5);
      var3 = null;
      var1.setline(119);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getstate$4, PyString.fromInterned("Return internal state; can be passed to setstate() later."));
      var1.setlocal("getstate", var5);
      var3 = null;
      var1.setline(123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setstate$5, PyString.fromInterned("Restore internal state from object returned by getstate()."));
      var1.setlocal("setstate", var5);
      var3 = null;
      var1.setline(145);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, jumpahead$7, PyString.fromInterned("Change the internal state to one that is likely far away\n        from the current state.  This method will not be in Py3.x,\n        so it is better to simply reseed.\n        "));
      var1.setlocal("jumpahead", var5);
      var3 = null;
      var1.setline(162);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$8, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(165);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$9, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      var1.setline(168);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$10, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      var1.setline(173);
      var4 = new PyObject[]{var1.getname("None"), Py.newInteger(1), var1.getname("int"), var1.getname("None"), Py.newLong("1")._lshift(var1.getname("BPF"))};
      var5 = new PyFunction(var1.f_globals, var4, randrange$11, PyString.fromInterned("Choose a random item from range(start, stop[, step]).\n\n        This fixes the problem with randint() which includes the\n        endpoint; in Python this is usually not what you want.\n        Do not supply the 'int', 'default', and 'maxwidth' arguments.\n        "));
      var1.setlocal("randrange", var5);
      var3 = null;
      var1.setline(237);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, randint$12, PyString.fromInterned("Return random integer in range [a, b], including both end points.\n        "));
      var1.setlocal("randint", var5);
      var3 = null;
      var1.setline(243);
      var4 = new PyObject[]{var1.getname("_log"), var1.getname("int"), Py.newLong("1")._lshift(var1.getname("BPF")), var1.getname("_MethodType"), var1.getname("_BuiltinMethodType")};
      var5 = new PyFunction(var1.f_globals, var4, _randbelow$13, PyString.fromInterned("Return a random int in the range [0,n)\n\n        Handles the case where n has more bits than returned\n        by a single call to the underlying generator.\n        "));
      var1.setlocal("_randbelow", var5);
      var3 = null;
      var1.setline(272);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, choice$14, PyString.fromInterned("Choose a random element from a non-empty sequence."));
      var1.setlocal("choice", var5);
      var3 = null;
      var1.setline(276);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("int")};
      var5 = new PyFunction(var1.f_globals, var4, shuffle$15, PyString.fromInterned("x, random=random.random -> shuffle list x in place; return None.\n\n        Optional arg random is a 0-argument function returning a random\n        float in [0.0, 1.0); by default, the standard random.random.\n        "));
      var1.setlocal("shuffle", var5);
      var3 = null;
      var1.setline(290);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, sample$16, PyString.fromInterned("Chooses k unique random elements from a population sequence.\n\n        Returns a new list containing elements from the population while\n        leaving the original population unchanged.  The resulting list is\n        in selection order so that all sub-slices will also be valid random\n        samples.  This allows raffle winners (the sample) to be partitioned\n        into grand prize and second place winners (the subslices).\n\n        Members of the population need not be hashable or unique.  If the\n        population contains repeats, then each occurrence is a possible\n        selection in the sample.\n\n        To choose a sample in a range of integers, use xrange as an argument.\n        This is especially fast and space efficient for sampling from a\n        large population:   sample(xrange(10000000), 60)\n        "));
      var1.setlocal("sample", var5);
      var3 = null;
      var1.setline(355);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, uniform$17, PyString.fromInterned("Get a random number in the range [a, b) or [a, b] depending on rounding."));
      var1.setlocal("uniform", var5);
      var3 = null;
      var1.setline(361);
      var4 = new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, triangular$18, PyString.fromInterned("Triangular distribution.\n\n        Continuous distribution bounded by given lower and upper limits,\n        and having a given mode value in-between.\n\n        http://en.wikipedia.org/wiki/Triangular_distribution\n\n        "));
      var1.setlocal("triangular", var5);
      var3 = null;
      var1.setline(380);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, normalvariate$19, PyString.fromInterned("Normal distribution.\n\n        mu is the mean, and sigma is the standard deviation.\n\n        "));
      var1.setlocal("normalvariate", var5);
      var3 = null;
      var1.setline(405);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, lognormvariate$20, PyString.fromInterned("Log normal distribution.\n\n        If you take the natural logarithm of this distribution, you'll get a\n        normal distribution with mean mu and standard deviation sigma.\n        mu can have any value, and sigma must be greater than zero.\n\n        "));
      var1.setlocal("lognormvariate", var5);
      var3 = null;
      var1.setline(417);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, expovariate$21, PyString.fromInterned("Exponential distribution.\n\n        lambd is 1.0 divided by the desired mean.  It should be\n        nonzero.  (The parameter would be called \"lambda\", but that is\n        a reserved word in Python.)  Returned values range from 0 to\n        positive infinity if lambd is positive, and from negative\n        infinity to 0 if lambd is negative.\n\n        "));
      var1.setlocal("expovariate", var5);
      var3 = null;
      var1.setline(436);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, vonmisesvariate$22, PyString.fromInterned("Circular data distribution.\n\n        mu is the mean angle, expressed in radians between 0 and 2*pi, and\n        kappa is the concentration parameter, which must be greater than or\n        equal to zero.  If kappa is equal to zero, this distribution reduces\n        to a uniform random angle over the range 0 to 2*pi.\n\n        "));
      var1.setlocal("vonmisesvariate", var5);
      var3 = null;
      var1.setline(484);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, gammavariate$23, PyString.fromInterned("Gamma distribution.  Not the gamma function!\n\n        Conditions on the parameters are alpha > 0 and beta > 0.\n\n        The probability distribution function is:\n\n                    x ** (alpha - 1) * math.exp(-x / beta)\n          pdf(x) =  --------------------------------------\n                      math.gamma(alpha) * beta ** alpha\n\n        "));
      var1.setlocal("gammavariate", var5);
      var3 = null;
      var1.setline(556);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, gauss$24, PyString.fromInterned("Gaussian distribution.\n\n        mu is the mean, and sigma is the standard deviation.  This is\n        slightly faster than the normalvariate() function.\n\n        Not thread-safe without a lock around calls.\n\n        "));
      var1.setlocal("gauss", var5);
      var3 = null;
      var1.setline(609);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, betavariate$25, PyString.fromInterned("Beta distribution.\n\n        Conditions on the parameters are alpha > 0 and beta > 0.\n        Returned values range between 0 and 1.\n\n        "));
      var1.setlocal("betavariate", var5);
      var3 = null;
      var1.setline(627);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, paretovariate$26, PyString.fromInterned("Pareto distribution.  alpha is the shape parameter."));
      var1.setlocal("paretovariate", var5);
      var3 = null;
      var1.setline(636);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, weibullvariate$27, PyString.fromInterned("Weibull distribution.\n\n        alpha is the scale parameter and beta is the shape parameter.\n\n        "));
      var1.setlocal("weibullvariate", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyString.fromInterned("Initialize an instance.\n\n        Optional argument x controls seeding, as for Random.seed().\n        ");
      var1.setline(97);
      var1.getlocal(0).__getattr__("seed").__call__(var2, var1.getlocal(1));
      var1.setline(98);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("gauss_next", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seed$3(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Initialize internal state from hashable object.\n\n        None or no argument seeds from current time or from an operating\n        system specific randomness source if available.\n\n        If a is not None or an int or long, hash(a) is used instead.\n        ");
      var1.setline(109);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(111);
            var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getglobal("_hexlify").__call__(var2, var1.getglobal("_urandom").__call__((ThreadState)var2, (PyObject)Py.newInteger(16))), (PyObject)Py.newInteger(16));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("NotImplementedError"))) {
               throw var6;
            }

            var1.setline(113);
            PyObject var4 = imp.importOne("time", var1, -1);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(114);
            var4 = var1.getglobal("long").__call__(var2, var1.getlocal(2).__getattr__("time").__call__(var2)._mul(Py.newInteger(256)));
            var1.setlocal(1, var4);
            var4 = null;
         }
      }

      var1.setline(116);
      var1.getglobal("super").__call__(var2, var1.getglobal("Random"), var1.getlocal(0)).__getattr__("seed").__call__(var2, var1.getlocal(1));
      var1.setline(117);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("gauss_next", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$4(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("Return internal state; can be passed to setstate() later.");
      var1.setline(121);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("VERSION"), var1.getglobal("super").__call__(var2, var1.getglobal("Random"), var1.getlocal(0)).__getattr__("getstate").__call__(var2), var1.getlocal(0).__getattr__("gauss_next")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$5(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Restore internal state from object returned by getstate().");
      var1.setline(125);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var3 = var1.getlocal(1);
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.getlocal(0).__setattr__("gauss_next", var5);
         var5 = null;
         var3 = null;
         var1.setline(128);
         var1.getglobal("super").__call__(var2, var1.getglobal("Random"), var1.getlocal(0)).__getattr__("setstate").__call__(var2, var1.getlocal(3));
      } else {
         var1.setline(129);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(141);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("state with version %s passed to Random.setstate() of version %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("VERSION")}))));
         }

         var1.setline(130);
         var3 = var1.getlocal(1);
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.getlocal(0).__setattr__("gauss_next", var5);
         var5 = null;
         var3 = null;

         try {
            var1.setline(136);
            var10000 = var1.getglobal("tuple");
            var1.setline(136);
            PyObject[] var10 = Py.EmptyObjects;
            PyFunction var8 = new PyFunction(var1.f_globals, var10, f$6, (PyObject)null);
            PyObject var10002 = var8.__call__(var2, var1.getlocal(3).__iter__());
            Arrays.fill(var10, (Object)null);
            var3 = var10000.__call__(var2, var10002);
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (var9.match(var1.getglobal("ValueError"))) {
               PyObject var7 = var9.value;
               var1.setlocal(5, var7);
               var4 = null;
               var1.setline(138);
               throw Py.makeException(var1.getglobal("TypeError"), var1.getlocal(5));
            }

            throw var9;
         }

         var1.setline(139);
         var1.getglobal("super").__call__(var2, var1.getglobal("Random"), var1.getlocal(0)).__getattr__("setstate").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$6(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(136);
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

      var1.setline(136);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(136);
         var1.setline(136);
         var6 = var1.getglobal("long").__call__(var2, var1.getlocal(1))._mod(Py.newInteger(2)._pow(Py.newInteger(32)));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject jumpahead$7(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyString.fromInterned("Change the internal state to one that is likely far away\n        from the current state.  This method will not be in Py3.x,\n        so it is better to simply reseed.\n        ");
      var1.setline(153);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1))._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("getstate").__call__(var2)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(154);
      var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getglobal("_hashlib").__getattr__("new").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sha512"), (PyObject)var1.getlocal(2)).__getattr__("hexdigest").__call__(var2), (PyObject)Py.newInteger(16));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      var1.getglobal("super").__call__(var2, var1.getglobal("Random"), var1.getlocal(0)).__getattr__("jumpahead").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$8(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getlocal(0).__getattr__("getstate").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$9(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      var1.getlocal(0).__getattr__("setstate").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __reduce__$10(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(Py.EmptyObjects), var1.getlocal(0).__getattr__("getstate").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject randrange$11(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Choose a random item from range(start, stop[, step]).\n\n        This fixes the problem with randint() which includes the\n        endpoint; in Python this is usually not what you want.\n        Do not supply the 'int', 'default', and 'maxwidth' arguments.\n        ");
      var1.setline(184);
      PyObject var3 = var1.getlocal(4).__call__(var2, var1.getlocal(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("non-integer arg 1 for randrange()"));
      } else {
         var1.setline(187);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getlocal(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(188);
            var3 = var1.getlocal(7);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(189);
               var3 = var1.getlocal(7);
               var10000 = var3._ge(var1.getlocal(6));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(190);
                  var3 = var1.getlocal(0).__getattr__("_randbelow").__call__(var2, var1.getlocal(7));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(191);
                  var3 = var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("random").__call__(var2)._mul(var1.getlocal(7)));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(192);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("empty range for randrange()"));
            }
         } else {
            var1.setline(195);
            PyObject var4 = var1.getlocal(4).__call__(var2, var1.getlocal(2));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(196);
            var4 = var1.getlocal(8);
            var10000 = var4._ne(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(197);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("non-integer stop for randrange()"));
            } else {
               var1.setline(198);
               var4 = var1.getlocal(8)._sub(var1.getlocal(7));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(199);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(9);
                  var10000 = var4._gt(Py.newInteger(0));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(213);
                  var4 = var1.getlocal(9);
                  var10000 = var4._ge(var1.getlocal(6));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(214);
                     var3 = var1.getlocal(4).__call__(var2, var1.getlocal(7)._add(var1.getlocal(0).__getattr__("_randbelow").__call__(var2, var1.getlocal(9))));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(215);
                     var3 = var1.getlocal(4).__call__(var2, var1.getlocal(7)._add(var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("random").__call__(var2)._mul(var1.getlocal(9)))));
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(216);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(217);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("empty range for randrange() (%d,%d, %d)")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)})));
                  } else {
                     var1.setline(220);
                     var4 = var1.getlocal(4).__call__(var2, var1.getlocal(3));
                     var1.setlocal(10, var4);
                     var4 = null;
                     var1.setline(221);
                     var4 = var1.getlocal(10);
                     var10000 = var4._ne(var1.getlocal(3));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(222);
                        throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("non-integer step for randrange()"));
                     } else {
                        var1.setline(223);
                        var4 = var1.getlocal(10);
                        var10000 = var4._gt(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(224);
                           var4 = var1.getlocal(9)._add(var1.getlocal(10))._sub(Py.newInteger(1))._floordiv(var1.getlocal(10));
                           var1.setlocal(11, var4);
                           var4 = null;
                        } else {
                           var1.setline(225);
                           var4 = var1.getlocal(10);
                           var10000 = var4._lt(Py.newInteger(0));
                           var4 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(228);
                              throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("zero step for randrange()"));
                           }

                           var1.setline(226);
                           var4 = var1.getlocal(9)._add(var1.getlocal(10))._add(Py.newInteger(1))._floordiv(var1.getlocal(10));
                           var1.setlocal(11, var4);
                           var4 = null;
                        }

                        var1.setline(230);
                        var4 = var1.getlocal(11);
                        var10000 = var4._le(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(231);
                           throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("empty range for randrange()"));
                        } else {
                           var1.setline(233);
                           var4 = var1.getlocal(11);
                           var10000 = var4._ge(var1.getlocal(6));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(234);
                              var3 = var1.getlocal(7)._add(var1.getlocal(10)._mul(var1.getlocal(0).__getattr__("_randbelow").__call__(var2, var1.getlocal(11))));
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(235);
                              var3 = var1.getlocal(7)._add(var1.getlocal(10)._mul(var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("random").__call__(var2)._mul(var1.getlocal(11)))));
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject randint$12(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Return random integer in range [a, b], including both end points.\n        ");
      var1.setline(241);
      PyObject var3 = var1.getlocal(0).__getattr__("randrange").__call__(var2, var1.getlocal(1), var1.getlocal(2)._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _randbelow$13(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyString.fromInterned("Return a random int in the range [0,n)\n\n        Handles the case where n has more bits than returned\n        by a single call to the underlying generator.\n        ");

      PyObject var10000;
      PyException var3;
      PyObject var4;
      PyObject var6;
      label42: {
         try {
            var1.setline(252);
            var6 = var1.getlocal(0).__getattr__("getrandbits");
            var1.setlocal(7, var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("AttributeError"))) {
               var1.setline(254);
               break label42;
            }

            throw var3;
         }

         var1.setline(259);
         var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("random"));
         var10000 = var4._is(var1.getlocal(6));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getglobal("type").__call__(var2, var1.getlocal(7));
            var10000 = var4._is(var1.getlocal(5));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(260);
            var4 = var1.getlocal(3).__call__(var2, Py.newFloat(1.00001)._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._sub(Py.newInteger(1)), (PyObject)Py.newFloat(2.0))));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(261);
            var4 = var1.getlocal(7).__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var4);
            var4 = null;

            while(true) {
               var1.setline(262);
               var4 = var1.getlocal(9);
               var10000 = var4._ge(var1.getlocal(1));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(264);
                  var4 = var1.getlocal(9);
                  var1.f_lasti = -1;
                  return var4;
               }

               var1.setline(263);
               var4 = var1.getlocal(7).__call__(var2, var1.getlocal(8));
               var1.setlocal(9, var4);
               var4 = null;
            }
         }
      }

      var1.setline(265);
      var6 = var1.getlocal(1);
      var10000 = var6._ge(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(266);
         var1.getglobal("_warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Underlying random() generator does not supply \nenough bits to choose from a population range this large"));
      }

      var1.setline(268);
      var4 = var1.getlocal(3).__call__(var2, var1.getlocal(0).__getattr__("random").__call__(var2)._mul(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject choice$14(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyString.fromInterned("Choose a random element from a non-empty sequence.");
      var1.setline(274);
      PyObject var3 = var1.getlocal(1).__getitem__(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("random").__call__(var2)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shuffle$15(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("x, random=random.random -> shuffle list x in place; return None.\n\n        Optional arg random is a 0-argument function returning a random\n        float in [0.0, 1.0); by default, the standard random.random.\n        ");
      var1.setline(283);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(284);
         var3 = var1.getlocal(0).__getattr__("random");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(285);
      var3 = var1.getglobal("reversed").__call__(var2, var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)))).__iter__();

      while(true) {
         var1.setline(285);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(287);
         PyObject var5 = var1.getlocal(3).__call__(var2, var1.getlocal(2).__call__(var2)._mul(var1.getlocal(4)._add(Py.newInteger(1))));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(288);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(var1.getlocal(5)), var1.getlocal(1).__getitem__(var1.getlocal(4))});
         PyObject[] var6 = Py.unpackSequence(var8, 2);
         PyObject var7 = var6[0];
         var1.getlocal(1).__setitem__(var1.getlocal(4), var7);
         var7 = null;
         var7 = var6[1];
         var1.getlocal(1).__setitem__(var1.getlocal(5), var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject sample$16(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Chooses k unique random elements from a population sequence.\n\n        Returns a new list containing elements from the population while\n        leaving the original population unchanged.  The resulting list is\n        in selection order so that all sub-slices will also be valid random\n        samples.  This allows raffle winners (the sample) to be partitioned\n        into grand prize and second place winners (the subslices).\n\n        Members of the population need not be hashable or unique.  If the\n        population contains repeats, then each occurrence is a possible\n        selection in the sample.\n\n        To choose a sample in a range of integers, use xrange as an argument.\n        This is especially fast and space efficient for sampling from a\n        large population:   sample(xrange(10000000), 60)\n        ");
      var1.setline(318);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(319);
      PyInteger var7 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var7;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(var1.getlocal(3));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(320);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sample larger than population")));
      } else {
         var1.setline(321);
         var3 = var1.getlocal(0).__getattr__("random");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(322);
         var3 = var1.getglobal("int");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(323);
         var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(324);
         var7 = Py.newInteger(21);
         var1.setlocal(7, var7);
         var3 = null;
         var1.setline(325);
         var3 = var1.getlocal(2);
         PyObject var9 = var3._gt(Py.newInteger(5));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(326);
            var3 = var1.getlocal(7);
            var3 = var3._iadd(Py.newInteger(4)._pow(var1.getglobal("_ceil").__call__(var2, var1.getglobal("_log").__call__((ThreadState)var2, (PyObject)var1.getlocal(2)._mul(Py.newInteger(3)), (PyObject)Py.newInteger(4)))));
            var1.setlocal(7, var3);
         }

         var1.setline(327);
         var3 = var1.getlocal(3);
         var9 = var3._le(var1.getlocal(7));
         var3 = null;
         if (!var9.__nonzero__()) {
            var9 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("keys"));
         }

         PyObject var5;
         if (var9.__nonzero__()) {
            var1.setline(330);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(331);
            var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(2)).__iter__();

            while(true) {
               var1.setline(331);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(9, var4);
               var1.setline(332);
               var5 = var1.getlocal(5).__call__(var2, var1.getlocal(4).__call__(var2)._mul(var1.getlocal(3)._sub(var1.getlocal(9))));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(333);
               var5 = var1.getlocal(8).__getitem__(var1.getlocal(10));
               var1.getlocal(6).__setitem__(var1.getlocal(9), var5);
               var5 = null;
               var1.setline(334);
               var5 = var1.getlocal(8).__getitem__(var1.getlocal(3)._sub(var1.getlocal(9))._sub(Py.newInteger(1)));
               var1.getlocal(8).__setitem__(var1.getlocal(10), var5);
               var5 = null;
            }
         } else {
            try {
               var1.setline(337);
               var3 = var1.getglobal("set").__call__(var2);
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(338);
               var3 = var1.getlocal(11).__getattr__("add");
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(339);
               var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(2)).__iter__();

               while(true) {
                  var1.setline(339);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(9, var4);
                  var1.setline(340);
                  var5 = var1.getlocal(5).__call__(var2, var1.getlocal(4).__call__(var2)._mul(var1.getlocal(3)));
                  var1.setlocal(10, var5);
                  var5 = null;

                  while(true) {
                     var1.setline(341);
                     var5 = var1.getlocal(10);
                     var9 = var5._in(var1.getlocal(11));
                     var5 = null;
                     if (!var9.__nonzero__()) {
                        var1.setline(343);
                        var1.getlocal(12).__call__(var2, var1.getlocal(10));
                        var1.setline(344);
                        var5 = var1.getlocal(1).__getitem__(var1.getlocal(10));
                        var1.getlocal(6).__setitem__(var1.getlocal(9), var5);
                        var5 = null;
                        break;
                     }

                     var1.setline(342);
                     var5 = var1.getlocal(5).__call__(var2, var1.getlocal(4).__call__(var2)._mul(var1.getlocal(3)));
                     var1.setlocal(10, var5);
                     var5 = null;
                  }
               }
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (var8.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("KeyError")}))) {
                  var1.setline(346);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__nonzero__()) {
                     var1.setline(347);
                     throw Py.makeException();
                  }

                  var1.setline(348);
                  var4 = var1.getlocal(0).__getattr__("sample").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var4;
               }

               throw var8;
            }
         }

         var1.setline(349);
         var4 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject uniform$17(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyString.fromInterned("Get a random number in the range [a, b) or [a, b] depending on rounding.");
      var1.setline(357);
      PyObject var3 = var1.getlocal(1)._add(var1.getlocal(2)._sub(var1.getlocal(1))._mul(var1.getlocal(0).__getattr__("random").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject triangular$18(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyString.fromInterned("Triangular distribution.\n\n        Continuous distribution bounded by given lower and upper limits,\n        and having a given mode value in-between.\n\n        http://en.wikipedia.org/wiki/Triangular_distribution\n\n        ");
      var1.setline(370);
      PyObject var3 = var1.getlocal(0).__getattr__("random").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(371);
      var1.setline(371);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      Object var6 = var10000.__nonzero__() ? Py.newFloat(0.5) : var1.getlocal(3)._sub(var1.getlocal(1))._truediv(var1.getlocal(2)._sub(var1.getlocal(1)));
      var1.setlocal(5, (PyObject)var6);
      var3 = null;
      var1.setline(372);
      var3 = var1.getlocal(4);
      var10000 = var3._gt(var1.getlocal(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(373);
         var3 = Py.newFloat(1.0)._sub(var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(374);
         var3 = Py.newFloat(1.0)._sub(var1.getlocal(5));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(375);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(376);
      var3 = var1.getlocal(1)._add(var1.getlocal(2)._sub(var1.getlocal(1))._mul(var1.getlocal(4)._mul(var1.getlocal(5))._pow(Py.newFloat(0.5))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject normalvariate$19(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyString.fromInterned("Normal distribution.\n\n        mu is the mean, and sigma is the standard deviation.\n\n        ");
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("random");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(394);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(395);
         var3 = var1.getlocal(3).__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(396);
         var3 = Py.newFloat(1.0)._sub(var1.getlocal(3).__call__(var2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(397);
         var3 = var1.getglobal("NV_MAGICCONST")._mul(var1.getlocal(4)._sub(Py.newFloat(0.5)))._truediv(var1.getlocal(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(398);
         var3 = var1.getlocal(6)._mul(var1.getlocal(6))._truediv(Py.newFloat(4.0));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(399);
         var3 = var1.getlocal(7);
         var10000 = var3._le(var1.getglobal("_log").__call__(var2, var1.getlocal(5)).__neg__());
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(401);
      var3 = var1.getlocal(1)._add(var1.getlocal(6)._mul(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lognormvariate$20(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned("Log normal distribution.\n\n        If you take the natural logarithm of this distribution, you'll get a\n        normal distribution with mean mu and standard deviation sigma.\n        mu can have any value, and sigma must be greater than zero.\n\n        ");
      var1.setline(413);
      PyObject var3 = var1.getglobal("_exp").__call__(var2, var1.getlocal(0).__getattr__("normalvariate").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expovariate$21(PyFrame var1, ThreadState var2) {
      var1.setline(426);
      PyString.fromInterned("Exponential distribution.\n\n        lambd is 1.0 divided by the desired mean.  It should be\n        nonzero.  (The parameter would be called \"lambda\", but that is\n        a reserved word in Python.)  Returned values range from 0 to\n        positive infinity if lambd is positive, and from negative\n        infinity to 0 if lambd is negative.\n\n        ");
      var1.setline(432);
      PyObject var3 = var1.getglobal("_log").__call__(var2, Py.newFloat(1.0)._sub(var1.getlocal(0).__getattr__("random").__call__(var2))).__neg__()._truediv(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject vonmisesvariate$22(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      PyString.fromInterned("Circular data distribution.\n\n        mu is the mean angle, expressed in radians between 0 and 2*pi, and\n        kappa is the concentration parameter, which must be greater than or\n        equal to zero.  If kappa is equal to zero, this distribution reduces\n        to a uniform random angle over the range 0 to 2*pi.\n\n        ");
      var1.setline(456);
      PyObject var3 = var1.getlocal(0).__getattr__("random");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(457);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._le(Py.newFloat(1.0E-6));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(458);
         var3 = var1.getglobal("TWOPI")._mul(var1.getlocal(3).__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(460);
         PyObject var4 = Py.newFloat(0.5)._truediv(var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(461);
         var4 = var1.getlocal(4)._add(var1.getglobal("_sqrt").__call__(var2, Py.newFloat(1.0)._add(var1.getlocal(4)._mul(var1.getlocal(4)))));
         var1.setlocal(5, var4);
         var4 = null;

         do {
            var1.setline(463);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(464);
            var4 = var1.getlocal(3).__call__(var2);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(465);
            var4 = var1.getglobal("_cos").__call__(var2, var1.getglobal("_pi")._mul(var1.getlocal(6)));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(467);
            var4 = var1.getlocal(7)._truediv(var1.getlocal(5)._add(var1.getlocal(7)));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(468);
            var4 = var1.getlocal(3).__call__(var2);
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(469);
            var4 = var1.getlocal(9);
            var10000 = var4._lt(Py.newFloat(1.0)._sub(var1.getlocal(8)._mul(var1.getlocal(8))));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(9);
               var10000 = var4._le(Py.newFloat(1.0)._sub(var1.getlocal(8))._mul(var1.getglobal("_exp").__call__(var2, var1.getlocal(8))));
               var4 = null;
            }
         } while(!var10000.__nonzero__());

         var1.setline(472);
         var4 = Py.newFloat(1.0)._truediv(var1.getlocal(5));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(473);
         var4 = var1.getlocal(10)._add(var1.getlocal(7))._truediv(Py.newFloat(1.0)._add(var1.getlocal(10)._mul(var1.getlocal(7))));
         var1.setlocal(11, var4);
         var4 = null;
         var1.setline(474);
         var4 = var1.getlocal(3).__call__(var2);
         var1.setlocal(12, var4);
         var4 = null;
         var1.setline(475);
         var4 = var1.getlocal(12);
         var10000 = var4._gt(Py.newFloat(0.5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(476);
            var4 = var1.getlocal(1)._add(var1.getglobal("_acos").__call__(var2, var1.getlocal(11)))._mod(var1.getglobal("TWOPI"));
            var1.setlocal(13, var4);
            var4 = null;
         } else {
            var1.setline(478);
            var4 = var1.getlocal(1)._sub(var1.getglobal("_acos").__call__(var2, var1.getlocal(11)))._mod(var1.getglobal("TWOPI"));
            var1.setlocal(13, var4);
            var4 = null;
         }

         var1.setline(480);
         var3 = var1.getlocal(13);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject gammavariate$23(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("Gamma distribution.  Not the gamma function!\n\n        Conditions on the parameters are alpha > 0 and beta > 0.\n\n        The probability distribution function is:\n\n                    x ** (alpha - 1) * math.exp(-x / beta)\n          pdf(x) =  --------------------------------------\n                      math.gamma(alpha) * beta ** alpha\n\n        ");
      var1.setline(501);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(Py.newFloat(0.0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._le(Py.newFloat(0.0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(502);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("gammavariate: alpha and beta must be > 0.0"));
      } else {
         var1.setline(504);
         var3 = var1.getlocal(0).__getattr__("random");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(505);
         var3 = var1.getlocal(1);
         var10000 = var3._gt(Py.newFloat(1.0));
         var3 = null;
         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(511);
            var3 = var1.getglobal("_sqrt").__call__(var2, Py.newFloat(2.0)._mul(var1.getlocal(1))._sub(Py.newFloat(1.0)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(512);
            var3 = var1.getlocal(1)._sub(var1.getglobal("LOG4"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(513);
            var3 = var1.getlocal(1)._add(var1.getlocal(4));
            var1.setlocal(6, var3);
            var3 = null;

            while(true) {
               var1.setline(515);
               if (!Py.newInteger(1).__nonzero__()) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(516);
               var3 = var1.getlocal(3).__call__(var2);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(517);
               PyFloat var5 = Py.newFloat(1.0E-7);
               PyObject var10001 = var1.getlocal(7);
               PyFloat var6 = var5;
               var3 = var10001;
               if ((var4 = var6._lt(var10001)).__nonzero__()) {
                  var4 = var3._lt(Py.newFloat(0.9999999));
               }

               var3 = null;
               if (!var4.__not__().__nonzero__()) {
                  var1.setline(519);
                  var3 = Py.newFloat(1.0)._sub(var1.getlocal(3).__call__(var2));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(520);
                  var3 = var1.getglobal("_log").__call__(var2, var1.getlocal(7)._truediv(Py.newFloat(1.0)._sub(var1.getlocal(7))))._truediv(var1.getlocal(4));
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(521);
                  var3 = var1.getlocal(1)._mul(var1.getglobal("_exp").__call__(var2, var1.getlocal(9)));
                  var1.setlocal(10, var3);
                  var3 = null;
                  var1.setline(522);
                  var3 = var1.getlocal(7)._mul(var1.getlocal(7))._mul(var1.getlocal(8));
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(523);
                  var3 = var1.getlocal(5)._add(var1.getlocal(6)._mul(var1.getlocal(9)))._sub(var1.getlocal(10));
                  var1.setlocal(12, var3);
                  var3 = null;
                  var1.setline(524);
                  var3 = var1.getlocal(12)._add(var1.getglobal("SG_MAGICCONST"))._sub(Py.newFloat(4.5)._mul(var1.getlocal(11)));
                  var10000 = var3._ge(Py.newFloat(0.0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(12);
                     var10000 = var3._ge(var1.getglobal("_log").__call__(var2, var1.getlocal(11)));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(525);
                     var3 = var1.getlocal(10)._mul(var1.getlocal(2));
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         } else {
            var1.setline(527);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(Py.newFloat(1.0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(529);
               var4 = var1.getlocal(3).__call__(var2);
               var1.setlocal(13, var4);
               var4 = null;

               while(true) {
                  var1.setline(530);
                  var4 = var1.getlocal(13);
                  var10000 = var4._le(Py.newFloat(1.0E-7));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(532);
                     var3 = var1.getglobal("_log").__call__(var2, var1.getlocal(13)).__neg__()._mul(var1.getlocal(2));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(531);
                  var4 = var1.getlocal(3).__call__(var2);
                  var1.setlocal(13, var4);
                  var4 = null;
               }
            } else {
               while(true) {
                  var1.setline(538);
                  if (!Py.newInteger(1).__nonzero__()) {
                     break;
                  }

                  var1.setline(539);
                  var4 = var1.getlocal(3).__call__(var2);
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(540);
                  var4 = var1.getglobal("_e")._add(var1.getlocal(1))._truediv(var1.getglobal("_e"));
                  var1.setlocal(14, var4);
                  var4 = null;
                  var1.setline(541);
                  var4 = var1.getlocal(14)._mul(var1.getlocal(13));
                  var1.setlocal(15, var4);
                  var4 = null;
                  var1.setline(542);
                  var4 = var1.getlocal(15);
                  var10000 = var4._le(Py.newFloat(1.0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(543);
                     var4 = var1.getlocal(15)._pow(Py.newFloat(1.0)._truediv(var1.getlocal(1)));
                     var1.setlocal(10, var4);
                     var4 = null;
                  } else {
                     var1.setline(545);
                     var4 = var1.getglobal("_log").__call__(var2, var1.getlocal(14)._sub(var1.getlocal(15))._truediv(var1.getlocal(1))).__neg__();
                     var1.setlocal(10, var4);
                     var4 = null;
                  }

                  var1.setline(546);
                  var4 = var1.getlocal(3).__call__(var2);
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(547);
                  var4 = var1.getlocal(15);
                  var10000 = var4._gt(Py.newFloat(1.0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(548);
                     var4 = var1.getlocal(7);
                     var10000 = var4._le(var1.getlocal(10)._pow(var1.getlocal(1)._sub(Py.newFloat(1.0))));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }
                  } else {
                     var1.setline(550);
                     var4 = var1.getlocal(7);
                     var10000 = var4._le(var1.getglobal("_exp").__call__(var2, var1.getlocal(10).__neg__()));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }
                  }
               }

               var1.setline(552);
               var3 = var1.getlocal(10)._mul(var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject gauss$24(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyString.fromInterned("Gaussian distribution.\n\n        mu is the mean, and sigma is the standard deviation.  This is\n        slightly faster than the normalvariate() function.\n\n        Not thread-safe without a lock around calls.\n\n        ");
      var1.setline(584);
      PyObject var3 = var1.getlocal(0).__getattr__("random");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(585);
      var3 = var1.getlocal(0).__getattr__("gauss_next");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(586);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("gauss_next", var3);
      var3 = null;
      var1.setline(587);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(588);
         var3 = var1.getlocal(3).__call__(var2)._mul(var1.getglobal("TWOPI"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(589);
         var3 = var1.getglobal("_sqrt").__call__(var2, Py.newFloat(-2.0)._mul(var1.getglobal("_log").__call__(var2, Py.newFloat(1.0)._sub(var1.getlocal(3).__call__(var2)))));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(590);
         var3 = var1.getglobal("_cos").__call__(var2, var1.getlocal(5))._mul(var1.getlocal(6));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(591);
         var3 = var1.getglobal("_sin").__call__(var2, var1.getlocal(5))._mul(var1.getlocal(6));
         var1.getlocal(0).__setattr__("gauss_next", var3);
         var3 = null;
      }

      var1.setline(593);
      var3 = var1.getlocal(1)._add(var1.getlocal(4)._mul(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject betavariate$25(PyFrame var1, ThreadState var2) {
      var1.setline(615);
      PyString.fromInterned("Beta distribution.\n\n        Conditions on the parameters are alpha > 0 and beta > 0.\n        Returned values range between 0 and 1.\n\n        ");
      var1.setline(619);
      PyObject var3 = var1.getlocal(0).__getattr__("gammavariate").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newFloat(1.0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(620);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(621);
         PyFloat var4 = Py.newFloat(0.0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(623);
         var3 = var1.getlocal(3)._truediv(var1.getlocal(3)._add(var1.getlocal(0).__getattr__("gammavariate").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newFloat(1.0))));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject paretovariate$26(PyFrame var1, ThreadState var2) {
      var1.setline(628);
      PyString.fromInterned("Pareto distribution.  alpha is the shape parameter.");
      var1.setline(631);
      PyObject var3 = Py.newFloat(1.0)._sub(var1.getlocal(0).__getattr__("random").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(632);
      var3 = Py.newFloat(1.0)._truediv(var1.getglobal("pow").__call__(var2, var1.getlocal(2), Py.newFloat(1.0)._truediv(var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject weibullvariate$27(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyString.fromInterned("Weibull distribution.\n\n        alpha is the scale parameter and beta is the shape parameter.\n\n        ");
      var1.setline(644);
      PyObject var3 = Py.newFloat(1.0)._sub(var1.getlocal(0).__getattr__("random").__call__(var2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(645);
      var3 = var1.getlocal(1)._mul(var1.getglobal("pow").__call__(var2, var1.getglobal("_log").__call__(var2, var1.getlocal(3)).__neg__(), Py.newFloat(1.0)._truediv(var1.getlocal(2))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject WichmannHill$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(651);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("VERSION", var3);
      var3 = null;
      var1.setline(653);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, seed$29, PyString.fromInterned("Initialize internal state from hashable object.\n\n        None or no argument seeds from current time or from an operating\n        system specific randomness source if available.\n\n        If a is not None or an int or long, hash(a) is used instead.\n\n        If a is an int or long, a is used directly.  Distinct values between\n        0 and 27814431486575L inclusive are guaranteed to yield distinct\n        internal states (this guarantee is specific to the default\n        Wichmann-Hill generator).\n        "));
      var1.setlocal("seed", var5);
      var3 = null;
      var1.setline(684);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, random$30, PyString.fromInterned("Get the next random number in the range [0.0, 1.0)."));
      var1.setlocal("random", var5);
      var3 = null;
      var1.setline(715);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getstate$31, PyString.fromInterned("Return internal state; can be passed to setstate() later."));
      var1.setlocal("getstate", var5);
      var3 = null;
      var1.setline(719);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setstate$32, PyString.fromInterned("Restore internal state from object returned by getstate()."));
      var1.setlocal("setstate", var5);
      var3 = null;
      var1.setline(729);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, jumpahead$33, PyString.fromInterned("Act as if n calls to random() were made, but quickly.\n\n        n is an int, greater than or equal to 0.\n\n        Example use:  If you have 2 threads and know that each will\n        consume no more than a million random numbers, create two Random\n        objects r1 and r2, then do\n            r2.setstate(r1.getstate())\n            r2.jumpahead(1000000)\n        Then r1 and r2 will use guaranteed-disjoint segments of the full\n        period.\n        "));
      var1.setlocal("jumpahead", var5);
      var3 = null;
      var1.setline(751);
      var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, _WichmannHill__whseed$34, PyString.fromInterned("Set the Wichmann-Hill seed from (x, y, z).\n\n        These must be integers in the range [0, 256).\n        "));
      var1.setlocal("_WichmannHill__whseed", var5);
      var3 = null;
      var1.setline(774);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, whseed$35, PyString.fromInterned("Seed from hashable object's hash code.\n\n        None or no argument seeds from current time.  It is not guaranteed\n        that objects with distinct hash codes lead to distinct internal\n        states.\n\n        This is obsolete, provided for compatibility with the seed routine\n        used prior to Python 2.1.  Use the .seed() method instead.\n        "));
      var1.setlocal("whseed", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject seed$29(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      PyString.fromInterned("Initialize internal state from hashable object.\n\n        None or no argument seeds from current time or from an operating\n        system specific randomness source if available.\n\n        If a is not None or an int or long, hash(a) is used instead.\n\n        If a is an int or long, a is used directly.  Distinct values between\n        0 and 27814431486575L inclusive are guaranteed to yield distinct\n        internal states (this guarantee is specific to the default\n        Wichmann-Hill generator).\n        ");
      var1.setline(667);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(669);
            var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getglobal("_hexlify").__call__(var2, var1.getglobal("_urandom").__call__((ThreadState)var2, (PyObject)Py.newInteger(16))), (PyObject)Py.newInteger(16));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("NotImplementedError"))) {
               throw var7;
            }

            var1.setline(671);
            PyObject var4 = imp.importOne("time", var1, -1);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(672);
            var4 = var1.getglobal("long").__call__(var2, var1.getlocal(2).__getattr__("time").__call__(var2)._mul(Py.newInteger(256)));
            var1.setlocal(1, var4);
            var4 = null;
         }
      }

      var1.setline(674);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(675);
         var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(677);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30268));
      PyObject[] var8 = Py.unpackSequence(var3, 2);
      PyObject var5 = var8[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(678);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30306));
      var8 = Py.unpackSequence(var3, 2);
      var5 = var8[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(679);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30322));
      var8 = Py.unpackSequence(var3, 2);
      var5 = var8[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(680);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(3))._add(Py.newInteger(1)), var1.getglobal("int").__call__(var2, var1.getlocal(4))._add(Py.newInteger(1)), var1.getglobal("int").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1))});
      var1.getlocal(0).__setattr__((String)"_seed", var9);
      var3 = null;
      var1.setline(682);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("gauss_next", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject random$30(PyFrame var1, ThreadState var2) {
      var1.setline(685);
      PyString.fromInterned("Get the next random number in the range [0.0, 1.0).");
      var1.setline(704);
      PyObject var3 = var1.getlocal(0).__getattr__("_seed");
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(705);
      var3 = Py.newInteger(171)._mul(var1.getlocal(1))._mod(Py.newInteger(30269));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(706);
      var3 = Py.newInteger(172)._mul(var1.getlocal(2))._mod(Py.newInteger(30307));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(707);
      var3 = Py.newInteger(170)._mul(var1.getlocal(3))._mod(Py.newInteger(30323));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(708);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.getlocal(0).__setattr__((String)"_seed", var6);
      var3 = null;
      var1.setline(713);
      var3 = var1.getlocal(1)._truediv(Py.newFloat(30269.0))._add(var1.getlocal(2)._truediv(Py.newFloat(30307.0)))._add(var1.getlocal(3)._truediv(Py.newFloat(30323.0)))._mod(Py.newFloat(1.0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getstate$31(PyFrame var1, ThreadState var2) {
      var1.setline(716);
      PyString.fromInterned("Return internal state; can be passed to setstate() later.");
      var1.setline(717);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("VERSION"), var1.getlocal(0).__getattr__("_seed"), var1.getlocal(0).__getattr__("gauss_next")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$32(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyString.fromInterned("Restore internal state from object returned by getstate().");
      var1.setline(721);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(722);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(723);
         var3 = var1.getlocal(1);
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_seed", var5);
         var5 = null;
         var5 = var4[2];
         var1.getlocal(0).__setattr__("gauss_next", var5);
         var5 = null;
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(725);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("state with version %s passed to Random.setstate() of version %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("VERSION")}))));
      }
   }

   public PyObject jumpahead$33(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyString.fromInterned("Act as if n calls to random() were made, but quickly.\n\n        n is an int, greater than or equal to 0.\n\n        Example use:  If you have 2 threads and know that each will\n        consume no more than a million random numbers, create two Random\n        objects r1 and r2, then do\n            r2.setstate(r1.getstate())\n            r2.jumpahead(1000000)\n        Then r1 and r2 will use guaranteed-disjoint segments of the full\n        period.\n        ");
      var1.setline(743);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(744);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("n must be >= 0")));
      } else {
         var1.setline(745);
         var3 = var1.getlocal(0).__getattr__("_seed");
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(746);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2)._mul(var1.getglobal("pow").__call__((ThreadState)var2, Py.newInteger(171), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30269))))._mod(Py.newInteger(30269));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(747);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3)._mul(var1.getglobal("pow").__call__((ThreadState)var2, Py.newInteger(172), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30307))))._mod(Py.newInteger(30307));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(748);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(4)._mul(var1.getglobal("pow").__call__((ThreadState)var2, Py.newInteger(170), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(30323))))._mod(Py.newInteger(30323));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(749);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
         var1.getlocal(0).__setattr__((String)"_seed", var6);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _WichmannHill__whseed$34(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      PyString.fromInterned("Set the Wichmann-Hill seed from (x, y, z).\n\n        These must be integers in the range [0, 256).\n        ");
      var1.setline(757);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10001 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._eq(var10001)).__nonzero__()) {
         var10001 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3;
         var3 = var10001;
         if ((var4 = var10000._eq(var10001)).__nonzero__()) {
            var4 = var3._eq(var1.getglobal("int"));
         }
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(758);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("seeds must be integers")));
      } else {
         var1.setline(759);
         PyInteger var6 = Py.newInteger(0);
         var10001 = var1.getlocal(1);
         PyInteger var9 = var6;
         var3 = var10001;
         if ((var4 = var9._le(var10001)).__nonzero__()) {
            var4 = var3._lt(Py.newInteger(256));
         }

         var10000 = var4;
         var3 = null;
         if (var4.__nonzero__()) {
            var6 = Py.newInteger(0);
            var10001 = var1.getlocal(2);
            var9 = var6;
            var3 = var10001;
            if ((var4 = var9._le(var10001)).__nonzero__()) {
               var4 = var3._lt(Py.newInteger(256));
            }

            var10000 = var4;
            var3 = null;
            if (var4.__nonzero__()) {
               var6 = Py.newInteger(0);
               var10001 = var1.getlocal(3);
               var9 = var6;
               var3 = var10001;
               if ((var4 = var9._le(var10001)).__nonzero__()) {
                  var4 = var3._lt(Py.newInteger(256));
               }

               var10000 = var4;
               var3 = null;
            }
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(760);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("seeds must be in range(0, 256)")));
         } else {
            var1.setline(761);
            var6 = Py.newInteger(0);
            var10001 = var1.getlocal(1);
            var9 = var6;
            var3 = var10001;
            if ((var4 = var9._eq(var10001)).__nonzero__()) {
               var10001 = var1.getlocal(2);
               var10000 = var3;
               var3 = var10001;
               if ((var4 = var10000._eq(var10001)).__nonzero__()) {
                  var4 = var3._eq(var1.getlocal(3));
               }
            }

            var3 = null;
            if (var4.__nonzero__()) {
               var1.setline(763);
               var3 = imp.importOne("time", var1, -1);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(764);
               var3 = var1.getglobal("long").__call__(var2, var1.getlocal(4).__getattr__("time").__call__(var2)._mul(Py.newInteger(256)));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(765);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(5)._and(Py.newInteger(16777215))._xor(var1.getlocal(5)._rshift(Py.newInteger(24))));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(766);
               var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(256));
               PyObject[] var7 = Py.unpackSequence(var3, 2);
               PyObject var5 = var7[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(1, var5);
               var5 = null;
               var3 = null;
               var1.setline(767);
               var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(256));
               var7 = Py.unpackSequence(var3, 2);
               var5 = var7[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(2, var5);
               var5 = null;
               var3 = null;
               var1.setline(768);
               var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(256));
               var7 = Py.unpackSequence(var3, 2);
               var5 = var7[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(770);
            PyTuple var10 = new PyTuple;
            PyObject[] var10002 = new PyObject[3];
            Object var10005 = var1.getlocal(1);
            if (!((PyObject)var10005).__nonzero__()) {
               var10005 = Py.newInteger(1);
            }

            var10002[0] = (PyObject)var10005;
            var10005 = var1.getlocal(2);
            if (!((PyObject)var10005).__nonzero__()) {
               var10005 = Py.newInteger(1);
            }

            var10002[1] = (PyObject)var10005;
            var10005 = var1.getlocal(3);
            if (!((PyObject)var10005).__nonzero__()) {
               var10005 = Py.newInteger(1);
            }

            var10002[2] = (PyObject)var10005;
            var10.<init>(var10002);
            PyTuple var8 = var10;
            var1.getlocal(0).__setattr__((String)"_seed", var8);
            var3 = null;
            var1.setline(772);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("gauss_next", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject whseed$35(PyFrame var1, ThreadState var2) {
      var1.setline(783);
      PyString.fromInterned("Seed from hashable object's hash code.\n\n        None or no argument seeds from current time.  It is not guaranteed\n        that objects with distinct hash codes lead to distinct internal\n        states.\n\n        This is obsolete, provided for compatibility with the seed routine\n        used prior to Python 2.1.  Use the .seed() method instead.\n        ");
      var1.setline(785);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(786);
         var1.getlocal(0).__getattr__("_WichmannHill__whseed").__call__(var2);
         var1.setline(787);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(788);
         var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(789);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(256));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(790);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(256));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(791);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(256));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(792);
         Object var7 = var1.getlocal(2)._add(var1.getlocal(1))._mod(Py.newInteger(256));
         if (!((PyObject)var7).__nonzero__()) {
            var7 = Py.newInteger(1);
         }

         Object var6 = var7;
         var1.setlocal(2, (PyObject)var6);
         var3 = null;
         var1.setline(793);
         var7 = var1.getlocal(3)._add(var1.getlocal(1))._mod(Py.newInteger(256));
         if (!((PyObject)var7).__nonzero__()) {
            var7 = Py.newInteger(1);
         }

         var6 = var7;
         var1.setlocal(3, (PyObject)var6);
         var3 = null;
         var1.setline(794);
         var7 = var1.getlocal(4)._add(var1.getlocal(1))._mod(Py.newInteger(256));
         if (!((PyObject)var7).__nonzero__()) {
            var7 = Py.newInteger(1);
         }

         var6 = var7;
         var1.setlocal(4, (PyObject)var6);
         var3 = null;
         var1.setline(795);
         var1.getlocal(0).__getattr__("_WichmannHill__whseed").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject SystemRandom$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Alternate random number generator using sources provided\n    by the operating system (such as /dev/urandom on Unix or\n    CryptGenRandom on Windows).\n\n     Not available on all systems (see os.urandom() for details).\n    "));
      var1.setline(805);
      PyString.fromInterned("Alternate random number generator using sources provided\n    by the operating system (such as /dev/urandom on Unix or\n    CryptGenRandom on Windows).\n\n     Not available on all systems (see os.urandom() for details).\n    ");
      var1.setline(807);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, random$37, PyString.fromInterned("Get the next random number in the range [0.0, 1.0)."));
      var1.setlocal("random", var4);
      var3 = null;
      var1.setline(811);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getrandbits$38, PyString.fromInterned("getrandbits(k) -> x.  Generates a long int with k random bits."));
      var1.setlocal("getrandbits", var4);
      var3 = null;
      var1.setline(821);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _stub$39, PyString.fromInterned("Stub method.  Not used for a system random number generator."));
      var1.setlocal("_stub", var4);
      var3 = null;
      var1.setline(824);
      PyObject var5 = var1.getname("_stub");
      var1.setlocal("seed", var5);
      var1.setlocal("jumpahead", var5);
      var1.setline(826);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _notimplemented$40, PyString.fromInterned("Method should not be called for a system random number generator."));
      var1.setlocal("_notimplemented", var4);
      var3 = null;
      var1.setline(829);
      var5 = var1.getname("_notimplemented");
      var1.setlocal("getstate", var5);
      var1.setlocal("setstate", var5);
      return var1.getf_locals();
   }

   public PyObject random$37(PyFrame var1, ThreadState var2) {
      var1.setline(808);
      PyString.fromInterned("Get the next random number in the range [0.0, 1.0).");
      var1.setline(809);
      PyObject var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getglobal("_hexlify").__call__(var2, var1.getglobal("_urandom").__call__((ThreadState)var2, (PyObject)Py.newInteger(7))), (PyObject)Py.newInteger(16))._rshift(Py.newInteger(3))._mul(var1.getglobal("RECIP_BPF"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getrandbits$38(PyFrame var1, ThreadState var2) {
      var1.setline(812);
      PyString.fromInterned("getrandbits(k) -> x.  Generates a long int with k random bits.");
      var1.setline(813);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(814);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("number of bits must be greater than zero")));
      } else {
         var1.setline(815);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getglobal("int").__call__(var2, var1.getlocal(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(816);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("number of bits should be an integer")));
         } else {
            var1.setline(817);
            var3 = var1.getlocal(1)._add(Py.newInteger(7))._floordiv(Py.newInteger(8));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(818);
            var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getglobal("_hexlify").__call__(var2, var1.getglobal("_urandom").__call__(var2, var1.getlocal(2))), (PyObject)Py.newInteger(16));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(819);
            var3 = var1.getlocal(3)._rshift(var1.getlocal(2)._mul(Py.newInteger(8))._sub(var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _stub$39(PyFrame var1, ThreadState var2) {
      var1.setline(822);
      PyString.fromInterned("Stub method.  Not used for a system random number generator.");
      var1.setline(823);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _notimplemented$40(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyString.fromInterned("Method should not be called for a system random number generator.");
      var1.setline(828);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("System entropy source does not have state.")));
   }

   public PyObject _test_generator$41(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(835);
      Py.printComma(var1.getlocal(0));
      Py.printComma(PyString.fromInterned("times"));
      Py.println(var1.getlocal(1).__getattr__("__name__"));
      var1.setline(836);
      PyFloat var7 = Py.newFloat(0.0);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(837);
      var7 = Py.newFloat(0.0);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(838);
      var7 = Py.newFloat(1.0E10);
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(839);
      var7 = Py.newFloat(-1.0E10);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(840);
      var3 = var1.getlocal(3).__getattr__("time").__call__(var2);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(841);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(841);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(847);
            var3 = var1.getlocal(3).__getattr__("time").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(848);
            Py.printComma(var1.getglobal("round").__call__((ThreadState)var2, (PyObject)var1.getlocal(11)._sub(var1.getlocal(8)), (PyObject)Py.newInteger(3)));
            Py.printComma(PyString.fromInterned("sec,"));
            var1.setline(849);
            var3 = var1.getlocal(4)._truediv(var1.getlocal(0));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(850);
            var3 = var1.getglobal("_sqrt").__call__(var2, var1.getlocal(5)._truediv(var1.getlocal(0))._sub(var1.getlocal(12)._mul(var1.getlocal(12))));
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(851);
            Py.println(PyString.fromInterned("avg %g, stddev %g, min %g, max %g")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(6), var1.getlocal(7)})));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(9, var4);
         var1.setline(842);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var5 = Py.EmptyObjects;
         String[] var6 = new String[0];
         var10000 = var10000._callextra(var5, var6, var1.getlocal(2), (PyObject)null);
         var5 = null;
         PyObject var8 = var10000;
         var1.setlocal(10, var8);
         var5 = null;
         var1.setline(843);
         var8 = var1.getlocal(4);
         var8 = var8._iadd(var1.getlocal(10));
         var1.setlocal(4, var8);
         var1.setline(844);
         var8 = var1.getlocal(5)._add(var1.getlocal(10)._mul(var1.getlocal(10)));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(845);
         var8 = var1.getglobal("min").__call__(var2, var1.getlocal(10), var1.getlocal(6));
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(846);
         var8 = var1.getglobal("max").__call__(var2, var1.getlocal(10), var1.getlocal(7));
         var1.setlocal(7, var8);
         var5 = null;
      }
   }

   public PyObject _test$42(PyFrame var1, ThreadState var2) {
      var1.setline(856);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("random"), (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.setline(857);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("normalvariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0)})));
      var1.setline(858);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("lognormvariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0)})));
      var1.setline(859);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("vonmisesvariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0)})));
      var1.setline(860);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.01), Py.newFloat(1.0)})));
      var1.setline(861);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.1), Py.newFloat(1.0)})));
      var1.setline(862);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.1), Py.newFloat(2.0)})));
      var1.setline(863);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.5), Py.newFloat(1.0)})));
      var1.setline(864);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.9), Py.newFloat(1.0)})));
      var1.setline(865);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(1.0), Py.newFloat(1.0)})));
      var1.setline(866);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(2.0), Py.newFloat(1.0)})));
      var1.setline(867);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(20.0), Py.newFloat(1.0)})));
      var1.setline(868);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gammavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(200.0), Py.newFloat(1.0)})));
      var1.setline(869);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("gauss"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0)})));
      var1.setline(870);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("betavariate"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(3.0), Py.newFloat(3.0)})));
      var1.setline(871);
      var1.getglobal("_test_generator").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("triangular"), (PyObject)(new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(1.0), Py.newFloat(1.0)._truediv(Py.newFloat(3.0))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public random$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 12288);
      var2 = new String[0];
      Random$1 = Py.newCode(0, var2, var1, "Random", 72, false, false, self, 1, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self", "x"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 91, false, false, self, 2, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "a", "time"};
      seed$3 = Py.newCode(2, var2, var1, "seed", 100, false, false, self, 3, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      getstate$4 = Py.newCode(1, var2, var1, "getstate", 119, false, false, self, 4, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "state", "version", "internalstate", "_(136_39)", "e"};
      setstate$5 = Py.newCode(2, var2, var1, "setstate", 123, false, false, self, 5, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"_(x)", "x"};
      f$6 = Py.newCode(1, var2, var1, "<genexpr>", 136, false, false, self, 6, (String[])null, (String[])null, 0, 12321);
      var2 = new String[]{"self", "n", "s"};
      jumpahead$7 = Py.newCode(2, var2, var1, "jumpahead", 145, false, false, self, 7, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __getstate__$8 = Py.newCode(1, var2, var1, "__getstate__", 162, false, false, self, 8, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "state"};
      __setstate__$9 = Py.newCode(2, var2, var1, "__setstate__", 165, false, false, self, 9, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$10 = Py.newCode(1, var2, var1, "__reduce__", 168, false, false, self, 10, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "start", "stop", "step", "int", "default", "maxwidth", "istart", "istop", "width", "istep", "n"};
      randrange$11 = Py.newCode(7, var2, var1, "randrange", 173, false, false, self, 11, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "a", "b"};
      randint$12 = Py.newCode(3, var2, var1, "randint", 237, false, false, self, 12, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "n", "_log", "int", "_maxwidth", "_Method", "_BuiltinMethod", "getrandbits", "k", "r"};
      _randbelow$13 = Py.newCode(7, var2, var1, "_randbelow", 243, false, false, self, 13, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "seq"};
      choice$14 = Py.newCode(2, var2, var1, "choice", 272, false, false, self, 14, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "x", "random", "int", "i", "j"};
      shuffle$15 = Py.newCode(4, var2, var1, "shuffle", 276, false, false, self, 15, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "population", "k", "n", "random", "_int", "result", "setsize", "pool", "i", "j", "selected", "selected_add"};
      sample$16 = Py.newCode(3, var2, var1, "sample", 290, false, false, self, 16, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "a", "b"};
      uniform$17 = Py.newCode(3, var2, var1, "uniform", 355, false, false, self, 17, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "low", "high", "mode", "u", "c"};
      triangular$18 = Py.newCode(4, var2, var1, "triangular", 361, false, false, self, 18, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "mu", "sigma", "random", "u1", "u2", "z", "zz"};
      normalvariate$19 = Py.newCode(3, var2, var1, "normalvariate", 380, false, false, self, 19, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "mu", "sigma"};
      lognormvariate$20 = Py.newCode(3, var2, var1, "lognormvariate", 405, false, false, self, 20, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "lambd"};
      expovariate$21 = Py.newCode(2, var2, var1, "expovariate", 417, false, false, self, 21, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "mu", "kappa", "random", "s", "r", "u1", "z", "d", "u2", "q", "f", "u3", "theta"};
      vonmisesvariate$22 = Py.newCode(3, var2, var1, "vonmisesvariate", 436, false, false, self, 22, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "alpha", "beta", "random", "ainv", "bbb", "ccc", "u1", "u2", "v", "x", "z", "r", "u", "b", "p"};
      gammavariate$23 = Py.newCode(3, var2, var1, "gammavariate", 484, false, false, self, 23, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "mu", "sigma", "random", "z", "x2pi", "g2rad"};
      gauss$24 = Py.newCode(3, var2, var1, "gauss", 556, false, false, self, 24, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "alpha", "beta", "y"};
      betavariate$25 = Py.newCode(3, var2, var1, "betavariate", 609, false, false, self, 25, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "alpha", "u"};
      paretovariate$26 = Py.newCode(2, var2, var1, "paretovariate", 627, false, false, self, 26, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "alpha", "beta", "u"};
      weibullvariate$27 = Py.newCode(3, var2, var1, "weibullvariate", 636, false, false, self, 27, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      WichmannHill$28 = Py.newCode(0, var2, var1, "WichmannHill", 649, false, false, self, 28, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self", "a", "time", "x", "y", "z"};
      seed$29 = Py.newCode(2, var2, var1, "seed", 653, false, false, self, 29, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "x", "y", "z"};
      random$30 = Py.newCode(1, var2, var1, "random", 684, false, false, self, 30, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      getstate$31 = Py.newCode(1, var2, var1, "getstate", 715, false, false, self, 31, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "state", "version"};
      setstate$32 = Py.newCode(2, var2, var1, "setstate", 719, false, false, self, 32, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "n", "x", "y", "z"};
      jumpahead$33 = Py.newCode(2, var2, var1, "jumpahead", 729, false, false, self, 33, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "x", "y", "z", "time", "t"};
      _WichmannHill__whseed$34 = Py.newCode(4, var2, var1, "_WichmannHill__whseed", 751, false, false, self, 34, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "a", "x", "y", "z"};
      whseed$35 = Py.newCode(2, var2, var1, "whseed", 774, false, false, self, 35, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      SystemRandom$36 = Py.newCode(0, var2, var1, "SystemRandom", 799, false, false, self, 36, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self"};
      random$37 = Py.newCode(1, var2, var1, "random", 807, false, false, self, 37, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "k", "bytes", "x"};
      getrandbits$38 = Py.newCode(2, var2, var1, "getrandbits", 811, false, false, self, 38, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "args", "kwds"};
      _stub$39 = Py.newCode(3, var2, var1, "_stub", 821, true, true, self, 39, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "args", "kwds"};
      _notimplemented$40 = Py.newCode(3, var2, var1, "_notimplemented", 826, true, true, self, 40, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"n", "func", "args", "time", "total", "sqsum", "smallest", "largest", "t0", "i", "x", "t1", "avg", "stddev"};
      _test_generator$41 = Py.newCode(3, var2, var1, "_test_generator", 833, false, false, self, 41, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"N"};
      _test$42 = Py.newCode(1, var2, var1, "_test", 855, false, false, self, 42, (String[])null, (String[])null, 0, 12289);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new random$py("random$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(random$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Random$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.seed$3(var2, var3);
         case 4:
            return this.getstate$4(var2, var3);
         case 5:
            return this.setstate$5(var2, var3);
         case 6:
            return this.f$6(var2, var3);
         case 7:
            return this.jumpahead$7(var2, var3);
         case 8:
            return this.__getstate__$8(var2, var3);
         case 9:
            return this.__setstate__$9(var2, var3);
         case 10:
            return this.__reduce__$10(var2, var3);
         case 11:
            return this.randrange$11(var2, var3);
         case 12:
            return this.randint$12(var2, var3);
         case 13:
            return this._randbelow$13(var2, var3);
         case 14:
            return this.choice$14(var2, var3);
         case 15:
            return this.shuffle$15(var2, var3);
         case 16:
            return this.sample$16(var2, var3);
         case 17:
            return this.uniform$17(var2, var3);
         case 18:
            return this.triangular$18(var2, var3);
         case 19:
            return this.normalvariate$19(var2, var3);
         case 20:
            return this.lognormvariate$20(var2, var3);
         case 21:
            return this.expovariate$21(var2, var3);
         case 22:
            return this.vonmisesvariate$22(var2, var3);
         case 23:
            return this.gammavariate$23(var2, var3);
         case 24:
            return this.gauss$24(var2, var3);
         case 25:
            return this.betavariate$25(var2, var3);
         case 26:
            return this.paretovariate$26(var2, var3);
         case 27:
            return this.weibullvariate$27(var2, var3);
         case 28:
            return this.WichmannHill$28(var2, var3);
         case 29:
            return this.seed$29(var2, var3);
         case 30:
            return this.random$30(var2, var3);
         case 31:
            return this.getstate$31(var2, var3);
         case 32:
            return this.setstate$32(var2, var3);
         case 33:
            return this.jumpahead$33(var2, var3);
         case 34:
            return this._WichmannHill__whseed$34(var2, var3);
         case 35:
            return this.whseed$35(var2, var3);
         case 36:
            return this.SystemRandom$36(var2, var3);
         case 37:
            return this.random$37(var2, var3);
         case 38:
            return this.getrandbits$38(var2, var3);
         case 39:
            return this._stub$39(var2, var3);
         case 40:
            return this._notimplemented$40(var2, var3);
         case 41:
            return this._test_generator$41(var2, var3);
         case 42:
            return this._test$42(var2, var3);
         default:
            return null;
      }
   }
}
