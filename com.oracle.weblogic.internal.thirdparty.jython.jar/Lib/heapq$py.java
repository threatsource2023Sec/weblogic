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
@Filename("heapq.py")
public class heapq$py extends PyFunctionTable implements PyRunnable {
   static heapq$py self;
   static final PyCode f$0;
   static final PyCode cmp_lt$1;
   static final PyCode heappush$2;
   static final PyCode heappop$3;
   static final PyCode heapreplace$4;
   static final PyCode heappushpop$5;
   static final PyCode heapify$6;
   static final PyCode _heappushpop_max$7;
   static final PyCode _heapify_max$8;
   static final PyCode nlargest$9;
   static final PyCode nsmallest$10;
   static final PyCode _siftdown$11;
   static final PyCode _siftup$12;
   static final PyCode _siftdown_max$13;
   static final PyCode _siftup_max$14;
   static final PyCode merge$15;
   static final PyCode nsmallest$16;
   static final PyCode nlargest$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Heap queue algorithm (a.k.a. priority queue).\n\nHeaps are arrays for which a[k] <= a[2*k+1] and a[k] <= a[2*k+2] for\nall k, counting elements from 0.  For the sake of comparison,\nnon-existing elements are considered to be infinite.  The interesting\nproperty of a heap is that a[0] is always its smallest element.\n\nUsage:\n\nheap = []            # creates an empty heap\nheappush(heap, item) # pushes a new item on the heap\nitem = heappop(heap) # pops the smallest item from the heap\nitem = heap[0]       # smallest item on the heap without popping it\nheapify(x)           # transforms list into a heap, in-place, in linear time\nitem = heapreplace(heap, item) # pops and returns smallest item, and adds\n                               # new item; the heap size is unchanged\n\nOur API differs from textbook heap algorithms as follows:\n\n- We use 0-based indexing.  This makes the relationship between the\n  index for a node and the indexes for its children slightly less\n  obvious, but is more suitable since Python uses 0-based indexing.\n\n- Our heappop() method returns the smallest item, not the largest.\n\nThese two make it possible to view the heap as a regular Python list\nwithout surprises: heap[0] is the smallest item, and heap.sort()\nmaintains the heap invariant!\n"));
      var1.setline(31);
      PyString.fromInterned("Heap queue algorithm (a.k.a. priority queue).\n\nHeaps are arrays for which a[k] <= a[2*k+1] and a[k] <= a[2*k+2] for\nall k, counting elements from 0.  For the sake of comparison,\nnon-existing elements are considered to be infinite.  The interesting\nproperty of a heap is that a[0] is always its smallest element.\n\nUsage:\n\nheap = []            # creates an empty heap\nheappush(heap, item) # pushes a new item on the heap\nitem = heappop(heap) # pops the smallest item from the heap\nitem = heap[0]       # smallest item on the heap without popping it\nheapify(x)           # transforms list into a heap, in-place, in linear time\nitem = heapreplace(heap, item) # pops and returns smallest item, and adds\n                               # new item; the heap size is unchanged\n\nOur API differs from textbook heap algorithms as follows:\n\n- We use 0-based indexing.  This makes the relationship between the\n  index for a node and the indexes for its children slightly less\n  obvious, but is more suitable since Python uses 0-based indexing.\n\n- Our heappop() method returns the smallest item, not the largest.\n\nThese two make it possible to view the heap as a regular Python list\nwithout surprises: heap[0] is the smallest item, and heap.sort()\nmaintains the heap invariant!\n");
      var1.setline(35);
      PyString var3 = PyString.fromInterned("Heap queues\n\n[explanation by François Pinard]\n\nHeaps are arrays for which a[k] <= a[2*k+1] and a[k] <= a[2*k+2] for\nall k, counting elements from 0.  For the sake of comparison,\nnon-existing elements are considered to be infinite.  The interesting\nproperty of a heap is that a[0] is always its smallest element.\n\nThe strange invariant above is meant to be an efficient memory\nrepresentation for a tournament.  The numbers below are `k', not a[k]:\n\n                                   0\n\n                  1                                 2\n\n          3               4                5               6\n\n      7       8       9       10      11      12      13      14\n\n    15 16   17 18   19 20   21 22   23 24   25 26   27 28   29 30\n\n\nIn the tree above, each cell `k' is topping `2*k+1' and `2*k+2'.  In\nan usual binary tournament we see in sports, each cell is the winner\nover the two cells it tops, and we can trace the winner down the tree\nto see all opponents s/he had.  However, in many computer applications\nof such tournaments, we do not need to trace the history of a winner.\nTo be more memory efficient, when a winner is promoted, we try to\nreplace it by something else at a lower level, and the rule becomes\nthat a cell and the two cells it tops contain three different items,\nbut the top cell \"wins\" over the two topped cells.\n\nIf this heap invariant is protected at all time, index 0 is clearly\nthe overall winner.  The simplest algorithmic way to remove it and\nfind the \"next\" winner is to move some loser (let's say cell 30 in the\ndiagram above) into the 0 position, and then percolate this new 0 down\nthe tree, exchanging values, until the invariant is re-established.\nThis is clearly logarithmic on the total number of items in the tree.\nBy iterating over all items, you get an O(n ln n) sort.\n\nA nice feature of this sort is that you can efficiently insert new\nitems while the sort is going on, provided that the inserted items are\nnot \"better\" than the last 0'th element you extracted.  This is\nespecially useful in simulation contexts, where the tree holds all\nincoming events, and the \"win\" condition means the smallest scheduled\ntime.  When an event schedule other events for execution, they are\nscheduled into the future, so they can easily go into the heap.  So, a\nheap is a good structure for implementing schedulers (this is what I\nused for my MIDI sequencer :-).\n\nVarious structures for implementing schedulers have been extensively\nstudied, and heaps are good for this, as they are reasonably speedy,\nthe speed is almost constant, and the worst case is not much different\nthan the average case.  However, there are other representations which\nare more efficient overall, yet the worst cases might be terrible.\n\nHeaps are also very useful in big disk sorts.  You most probably all\nknow that a big sort implies producing \"runs\" (which are pre-sorted\nsequences, which size is usually related to the amount of CPU memory),\nfollowed by a merging passes for these runs, which merging is often\nvery cleverly organised[1].  It is very important that the initial\nsort produces the longest runs possible.  Tournaments are a good way\nto that.  If, using all the memory available to hold a tournament, you\nreplace and percolate items that happen to fit the current run, you'll\nproduce runs which are twice the size of the memory for random input,\nand much better for input fuzzily ordered.\n\nMoreover, if you output the 0'th item on disk and get an input which\nmay not fit in the current tournament (because the value \"wins\" over\nthe last output value), it cannot fit in the heap, so the size of the\nheap decreases.  The freed memory could be cleverly reused immediately\nfor progressively building a second heap, which grows at exactly the\nsame rate the first heap is melting.  When the first heap completely\nvanishes, you switch heaps and start a new run.  Clever and quite\neffective!\n\nIn a word, heaps are useful memory structures to know.  I use them in\na few applications, and I think it is good to keep a `heap' module\naround. :-)\n\n--------------------\n[1] The disk balancing algorithms which are current, nowadays, are\nmore annoying than clever, and this is a consequence of the seeking\ncapabilities of the disks.  On devices which cannot seek, like big\ntape drives, the story was quite different, and one had to be very\nclever to ensure (far in advance) that each tape movement will be the\nmost effective possible (that is, will best participate at\n\"progressing\" the merge).  Some tapes were even able to read\nbackwards, and this was also used to avoid the rewinding time.\nBelieve me, real good tape sorts were quite spectacular to watch!\nFrom all times, sorting has always been a Great Art! :-)\n");
      var1.setlocal("__about__", var3);
      var3 = null;
      var1.setline(129);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("heappush"), PyString.fromInterned("heappop"), PyString.fromInterned("heapify"), PyString.fromInterned("heapreplace"), PyString.fromInterned("merge"), PyString.fromInterned("nlargest"), PyString.fromInterned("nsmallest"), PyString.fromInterned("heappushpop")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(132);
      String[] var7 = new String[]{"islice", "count", "imap", "izip", "tee", "chain"};
      PyObject[] var8 = imp.importFrom("itertools", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("islice", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("count", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("imap", var4);
      var4 = null;
      var4 = var8[3];
      var1.setlocal("izip", var4);
      var4 = null;
      var4 = var8[4];
      var1.setlocal("tee", var4);
      var4 = null;
      var4 = var8[5];
      var1.setlocal("chain", var4);
      var4 = null;
      var1.setline(133);
      var7 = new String[]{"itemgetter"};
      var8 = imp.importFrom("operator", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("itemgetter", var4);
      var4 = null;
      var1.setline(135);
      var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, cmp_lt$1, (PyObject)null);
      var1.setlocal("cmp_lt", var9);
      var3 = null;
      var1.setline(140);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, heappush$2, PyString.fromInterned("Push item onto heap, maintaining the heap invariant."));
      var1.setlocal("heappush", var9);
      var3 = null;
      var1.setline(145);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, heappop$3, PyString.fromInterned("Pop the smallest item off the heap, maintaining the heap invariant."));
      var1.setlocal("heappop", var9);
      var3 = null;
      var1.setline(156);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, heapreplace$4, PyString.fromInterned("Pop and return the current smallest value, and add the new item.\n\n    This is more efficient than heappop() followed by heappush(), and can be\n    more appropriate when using a fixed-size heap.  Note that the value\n    returned may be larger than item!  That constrains reasonable uses of\n    this routine unless written as part of a conditional replacement:\n\n        if item > heap[0]:\n            item = heapreplace(heap, item)\n    "));
      var1.setlocal("heapreplace", var9);
      var3 = null;
      var1.setline(172);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, heappushpop$5, PyString.fromInterned("Fast version of a heappush followed by a heappop."));
      var1.setlocal("heappushpop", var9);
      var3 = null;
      var1.setline(179);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, heapify$6, PyString.fromInterned("Transform list into a heap, in-place, in O(len(x)) time."));
      var1.setlocal("heapify", var9);
      var3 = null;
      var1.setline(190);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _heappushpop_max$7, PyString.fromInterned("Maxheap version of a heappush followed by a heappop."));
      var1.setlocal("_heappushpop_max", var9);
      var3 = null;
      var1.setline(197);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _heapify_max$8, PyString.fromInterned("Transform list into a maxheap, in-place, in O(len(x)) time."));
      var1.setlocal("_heapify_max", var9);
      var3 = null;
      var1.setline(203);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, nlargest$9, PyString.fromInterned("Find the n largest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, reverse=True)[:n]\n    "));
      var1.setlocal("nlargest", var9);
      var3 = null;
      var1.setline(221);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, nsmallest$10, PyString.fromInterned("Find the n smallest elements in a dataset.\n\n    Equivalent to:  sorted(iterable)[:n]\n    "));
      var1.setlocal("nsmallest", var9);
      var3 = null;
      var1.setline(242);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _siftdown$11, (PyObject)null);
      var1.setlocal("_siftdown", var9);
      var3 = null;
      var1.setline(295);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _siftup$12, (PyObject)null);
      var1.setlocal("_siftup", var9);
      var3 = null;
      var1.setline(315);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _siftdown_max$13, PyString.fromInterned("Maxheap variant of _siftdown"));
      var1.setlocal("_siftdown_max", var9);
      var3 = null;
      var1.setline(330);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _siftup_max$14, PyString.fromInterned("Maxheap variant of _siftup"));
      var1.setlocal("_siftup_max", var9);
      var3 = null;

      try {
         var1.setline(353);
         imp.importAll("_heapq", var1, -1);
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(355);
      }

      var1.setline(357);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, merge$15, PyString.fromInterned("Merge multiple sorted inputs into a single sorted output.\n\n    Similar to sorted(itertools.chain(*iterables)) but returns a generator,\n    does not pull the data into memory all at once, and assumes that each of\n    the input streams is already sorted (smallest to largest).\n\n    >>> list(merge([1,3,5,7], [0,2,4,8], [5,10,15,20], [], [25]))\n    [0, 1, 2, 3, 4, 5, 5, 7, 8, 10, 15, 20, 25]\n\n    "));
      var1.setlocal("merge", var9);
      var3 = null;
      var1.setline(393);
      PyObject var11 = var1.getname("nsmallest");
      var1.setlocal("_nsmallest", var11);
      var3 = null;
      var1.setline(394);
      var8 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var8, nsmallest$16, PyString.fromInterned("Find the n smallest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, key=key)[:n]\n    "));
      var1.setlocal("nsmallest", var9);
      var3 = null;
      var1.setline(430);
      var11 = var1.getname("nlargest");
      var1.setlocal("_nlargest", var11);
      var3 = null;
      var1.setline(431);
      var8 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var8, nlargest$17, PyString.fromInterned("Find the n largest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, key=key, reverse=True)[:n]\n    "));
      var1.setlocal("nlargest", var9);
      var3 = null;
      var1.setline(468);
      var11 = var1.getname("__name__");
      PyObject var10000 = var11._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(470);
         var6 = new PyList(Py.EmptyObjects);
         var1.setlocal("heap", var6);
         var3 = null;
         var1.setline(471);
         var6 = new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(3), Py.newInteger(5), Py.newInteger(7), Py.newInteger(9), Py.newInteger(2), Py.newInteger(4), Py.newInteger(6), Py.newInteger(8), Py.newInteger(0)});
         var1.setlocal("data", var6);
         var3 = null;
         var1.setline(472);
         var11 = var1.getname("data").__iter__();

         label30:
         while(true) {
            var1.setline(472);
            var4 = var11.__iternext__();
            if (var4 == null) {
               var1.setline(474);
               var6 = new PyList(Py.EmptyObjects);
               var1.setlocal("sort", var6);
               var3 = null;

               while(true) {
                  var1.setline(475);
                  if (!var1.getname("heap").__nonzero__()) {
                     var1.setline(477);
                     Py.println(var1.getname("sort"));
                     var1.setline(479);
                     var11 = imp.importOne("doctest", var1, -1);
                     var1.setlocal("doctest", var11);
                     var3 = null;
                     var1.setline(480);
                     var1.getname("doctest").__getattr__("testmod").__call__(var2);
                     break label30;
                  }

                  var1.setline(476);
                  var1.getname("sort").__getattr__("append").__call__(var2, var1.getname("heappop").__call__(var2, var1.getname("heap")));
               }
            }

            var1.setlocal("item", var4);
            var1.setline(473);
            var1.getname("heappush").__call__(var2, var1.getname("heap"), var1.getname("item"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cmp_lt$1(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      var1.setline(138);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__lt__")).__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._lt(var1.getlocal(1));
         var3 = null;
      } else {
         var3 = var1.getlocal(1);
         var10000 = var3._le(var1.getlocal(0));
         var3 = null;
         var10000 = var10000.__not__();
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject heappush$2(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Push item onto heap, maintaining the heap invariant.");
      var1.setline(142);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(143);
      var1.getglobal("_siftdown").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(Py.newInteger(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject heappop$3(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyString.fromInterned("Pop the smallest item off the heap, maintaining the heap invariant.");
      var1.setline(147);
      PyObject var3 = var1.getlocal(0).__getattr__("pop").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(148);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(149);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(150);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var3);
         var3 = null;
         var1.setline(151);
         var1.getglobal("_siftup").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0));
      } else {
         var1.setline(153);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(154);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject heapreplace$4(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyString.fromInterned("Pop and return the current smallest value, and add the new item.\n\n    This is more efficient than heappop() followed by heappush(), and can be\n    more appropriate when using a fixed-size heap.  Note that the value\n    returned may be larger than item!  That constrains reasonable uses of\n    this routine unless written as part of a conditional replacement:\n\n        if item > heap[0]:\n            item = heapreplace(heap, item)\n    ");
      var1.setline(167);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var3);
      var3 = null;
      var1.setline(169);
      var1.getglobal("_siftup").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0));
      var1.setline(170);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject heappushpop$5(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("Fast version of a heappush followed by a heappop.");
      var1.setline(174);
      PyObject var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1));
      }

      if (var10000.__nonzero__()) {
         var1.setline(175);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1)});
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var5);
         var5 = null;
         var3 = null;
         var1.setline(176);
         var1.getglobal("_siftup").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0));
      }

      var1.setline(177);
      PyObject var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject heapify$6(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Transform list into a heap, in-place, in O(len(x)) time.");
      var1.setline(181);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("reversed").__call__(var2, var1.getglobal("xrange").__call__(var2, var1.getlocal(1)._floordiv(Py.newInteger(2)))).__iter__();

      while(true) {
         var1.setline(187);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(188);
         var1.getglobal("_siftup").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      }
   }

   public PyObject _heappushpop_max$7(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Maxheap version of a heappush followed by a heappop.");
      var1.setline(192);
      PyObject var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getitem__(Py.newInteger(0)));
      }

      if (var10000.__nonzero__()) {
         var1.setline(193);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1)});
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var5);
         var5 = null;
         var3 = null;
         var1.setline(194);
         var1.getglobal("_siftup_max").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0));
      }

      var1.setline(195);
      PyObject var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _heapify_max$8(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyString.fromInterned("Transform list into a maxheap, in-place, in O(len(x)) time.");
      var1.setline(199);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getglobal("reversed").__call__(var2, var1.getglobal("range").__call__(var2, var1.getlocal(1)._floordiv(Py.newInteger(2)))).__iter__();

      while(true) {
         var1.setline(200);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(201);
         var1.getglobal("_siftup_max").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      }
   }

   public PyObject nlargest$9(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyString.fromInterned("Find the n largest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, reverse=True)[:n]\n    ");
      var1.setline(208);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(209);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(210);
         PyObject var4 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(211);
         var4 = var1.getglobal("list").__call__(var2, var1.getglobal("islice").__call__(var2, var1.getlocal(2), var1.getlocal(0)));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(212);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(213);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(214);
            var1.getglobal("heapify").__call__(var2, var1.getlocal(3));
            var1.setline(215);
            var4 = var1.getglobal("heappushpop");
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(216);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(216);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(218);
                  var10000 = var1.getlocal(3).__getattr__("sort");
                  PyObject[] var8 = new PyObject[]{var1.getglobal("True")};
                  String[] var7 = new String[]{"reverse"};
                  var10000.__call__(var2, var8, var7);
                  var4 = null;
                  var1.setline(219);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var5);
               var1.setline(217);
               var1.getlocal(4).__call__(var2, var1.getlocal(3), var1.getlocal(5));
            }
         }
      }
   }

   public PyObject nsmallest$10(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyString.fromInterned("Find the n smallest elements in a dataset.\n\n    Equivalent to:  sorted(iterable)[:n]\n    ");
      var1.setline(226);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(227);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(228);
         PyObject var4 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(229);
         var4 = var1.getglobal("list").__call__(var2, var1.getglobal("islice").__call__(var2, var1.getlocal(2), var1.getlocal(0)));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(230);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(231);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(232);
            var1.getglobal("_heapify_max").__call__(var2, var1.getlocal(3));
            var1.setline(233);
            var4 = var1.getglobal("_heappushpop_max");
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(234);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(234);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(236);
                  var1.getlocal(3).__getattr__("sort").__call__(var2);
                  var1.setline(237);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var5);
               var1.setline(235);
               var1.getlocal(4).__call__(var2, var1.getlocal(3), var1.getlocal(5));
            }
         }
      }
   }

   public PyObject _siftdown$11(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(246);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._gt(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(247);
         var3 = var1.getlocal(2)._sub(Py.newInteger(1))._rshift(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(248);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(249);
         if (!var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(3), var1.getlocal(5)).__nonzero__()) {
            break;
         }

         var1.setline(250);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
         var3 = null;
         var1.setline(251);
         var3 = var1.getlocal(4);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(254);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _siftup$12(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(297);
      var3 = var1.getlocal(1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(298);
      var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(300);
      var3 = Py.newInteger(2)._mul(var1.getlocal(1))._add(Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(301);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(312);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.setline(313);
            var1.getglobal("_siftdown").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(303);
         var3 = var1.getlocal(5)._add(Py.newInteger(1));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(304);
         var3 = var1.getlocal(6);
         var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(5)), var1.getlocal(0).__getitem__(var1.getlocal(6))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(305);
            var3 = var1.getlocal(6);
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(307);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(5));
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(308);
         var3 = var1.getlocal(5);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(309);
         var3 = Py.newInteger(2)._mul(var1.getlocal(1))._add(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
      }
   }

   public PyObject _siftdown_max$13(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyString.fromInterned("Maxheap variant of _siftdown");
      var1.setline(317);
      PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(320);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._gt(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(321);
         var3 = var1.getlocal(2)._sub(Py.newInteger(1))._rshift(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(322);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(323);
         if (!var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(5), var1.getlocal(3)).__nonzero__()) {
            break;
         }

         var1.setline(324);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
         var3 = null;
         var1.setline(325);
         var3 = var1.getlocal(4);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(328);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _siftup_max$14(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyString.fromInterned("Maxheap variant of _siftup");
      var1.setline(332);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(333);
      var3 = var1.getlocal(1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(334);
      var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(336);
      var3 = Py.newInteger(2)._mul(var1.getlocal(1))._add(Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(337);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(348);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.setline(349);
            var1.getglobal("_siftdown_max").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(339);
         var3 = var1.getlocal(5)._add(Py.newInteger(1));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(340);
         var3 = var1.getlocal(6);
         var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("cmp_lt").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(6)), var1.getlocal(0).__getitem__(var1.getlocal(5))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(341);
            var3 = var1.getlocal(6);
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(343);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(5));
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(344);
         var3 = var1.getlocal(5);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(345);
         var3 = Py.newInteger(2)._mul(var1.getlocal(1))._add(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
      }
   }

   public PyObject merge$15(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject nsmallest$16(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyString.fromInterned("Find the n smallest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, key=key)[:n]\n    ");
      var1.setline(400);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyException var4;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(401);
         var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(402);
         var3 = var1.getglobal("list").__call__(var2, var1.getglobal("islice").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(403);
         PyList var10;
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(404);
            var10 = new PyList(Py.EmptyObjects);
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(405);
            var8 = var1.getlocal(2);
            var10000 = var8._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(406);
               var10 = new PyList(new PyObject[]{var1.getglobal("min").__call__(var2, var1.getglobal("chain").__call__(var2, var1.getlocal(4), var1.getlocal(3)))});
               var1.f_lasti = -1;
               return var10;
            } else {
               var1.setline(407);
               PyObject[] var10002 = new PyObject[1];
               PyObject var10005 = var1.getglobal("min");
               PyObject[] var13 = new PyObject[]{var1.getglobal("chain").__call__(var2, var1.getlocal(4), var1.getlocal(3)), var1.getlocal(2)};
               String[] var11 = new String[]{"key"};
               var10005 = var10005.__call__(var2, var13, var11);
               var4 = null;
               var10002[0] = var10005;
               var10 = new PyList(var10002);
               var1.f_lasti = -1;
               return var10;
            }
         }
      } else {
         PyObject[] var9;
         label34: {
            try {
               var1.setline(411);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var8);
               var4 = null;
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
                  var1.setline(413);
                  break label34;
               }

               throw var4;
            }

            var1.setline(415);
            PyObject var5 = var1.getlocal(0);
            var10000 = var5._ge(var1.getlocal(5));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(416);
               var10000 = var1.getglobal("sorted");
               var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
               String[] var12 = new String[]{"key"};
               var10000 = var10000.__call__(var2, var9, var12);
               var5 = null;
               var3 = var10000.__getslice__((PyObject)null, var1.getlocal(0), (PyObject)null);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(419);
         var8 = var1.getlocal(2);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(420);
            var8 = var1.getglobal("izip").__call__(var2, var1.getlocal(1), var1.getglobal("count").__call__(var2));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(421);
            var8 = var1.getglobal("_nsmallest").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(422);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(425);
            var8 = var1.getglobal("tee").__call__(var2, var1.getlocal(1));
            var9 = Py.unpackSequence(var8, 2);
            PyObject var6 = var9[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(426);
            var8 = var1.getglobal("izip").__call__(var2, var1.getglobal("imap").__call__(var2, var1.getlocal(2), var1.getlocal(7)), var1.getglobal("count").__call__(var2), var1.getlocal(8));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(427);
            var8 = var1.getglobal("_nsmallest").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(428);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)), var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject nlargest$17(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyString.fromInterned("Find the n largest elements in a dataset.\n\n    Equivalent to:  sorted(iterable, key=key, reverse=True)[:n]\n    ");
      var1.setline(438);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyException var4;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(439);
         var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(440);
         var3 = var1.getglobal("list").__call__(var2, var1.getglobal("islice").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(441);
         PyList var10;
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(442);
            var10 = new PyList(Py.EmptyObjects);
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(443);
            var8 = var1.getlocal(2);
            var10000 = var8._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(444);
               var10 = new PyList(new PyObject[]{var1.getglobal("max").__call__(var2, var1.getglobal("chain").__call__(var2, var1.getlocal(4), var1.getlocal(3)))});
               var1.f_lasti = -1;
               return var10;
            } else {
               var1.setline(445);
               PyObject[] var10002 = new PyObject[1];
               PyObject var10005 = var1.getglobal("max");
               PyObject[] var13 = new PyObject[]{var1.getglobal("chain").__call__(var2, var1.getlocal(4), var1.getlocal(3)), var1.getlocal(2)};
               String[] var11 = new String[]{"key"};
               var10005 = var10005.__call__(var2, var13, var11);
               var4 = null;
               var10002[0] = var10005;
               var10 = new PyList(var10002);
               var1.f_lasti = -1;
               return var10;
            }
         }
      } else {
         PyObject[] var9;
         label34: {
            try {
               var1.setline(449);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var8);
               var4 = null;
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
                  var1.setline(451);
                  break label34;
               }

               throw var4;
            }

            var1.setline(453);
            PyObject var5 = var1.getlocal(0);
            var10000 = var5._ge(var1.getlocal(5));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(454);
               var10000 = var1.getglobal("sorted");
               var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("True")};
               String[] var12 = new String[]{"key", "reverse"};
               var10000 = var10000.__call__(var2, var9, var12);
               var5 = null;
               var3 = var10000.__getslice__((PyObject)null, var1.getlocal(0), (PyObject)null);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(457);
         var8 = var1.getlocal(2);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(458);
            var8 = var1.getglobal("izip").__call__(var2, var1.getlocal(1), var1.getglobal("count").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(459);
            var8 = var1.getglobal("_nlargest").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(460);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(463);
            var8 = var1.getglobal("tee").__call__(var2, var1.getlocal(1));
            var9 = Py.unpackSequence(var8, 2);
            PyObject var6 = var9[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(464);
            var8 = var1.getglobal("izip").__call__(var2, var1.getglobal("imap").__call__(var2, var1.getlocal(2), var1.getlocal(7)), var1.getglobal("count").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)), var1.getlocal(8));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(465);
            var8 = var1.getglobal("_nlargest").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(466);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)), var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public heapq$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x", "y"};
      cmp_lt$1 = Py.newCode(2, var2, var1, "cmp_lt", 135, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "item"};
      heappush$2 = Py.newCode(2, var2, var1, "heappush", 140, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "lastelt", "returnitem"};
      heappop$3 = Py.newCode(1, var2, var1, "heappop", 145, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "item", "returnitem"};
      heapreplace$4 = Py.newCode(2, var2, var1, "heapreplace", 156, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "item"};
      heappushpop$5 = Py.newCode(2, var2, var1, "heappushpop", 172, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "n", "i"};
      heapify$6 = Py.newCode(1, var2, var1, "heapify", 179, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "item"};
      _heappushpop_max$7 = Py.newCode(2, var2, var1, "_heappushpop_max", 190, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "n", "i"};
      _heapify_max$8 = Py.newCode(1, var2, var1, "_heapify_max", 197, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "iterable", "it", "result", "_heappushpop", "elem"};
      nlargest$9 = Py.newCode(2, var2, var1, "nlargest", 203, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "iterable", "it", "result", "_heappushpop", "elem"};
      nsmallest$10 = Py.newCode(2, var2, var1, "nsmallest", 221, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "startpos", "pos", "newitem", "parentpos", "parent"};
      _siftdown$11 = Py.newCode(3, var2, var1, "_siftdown", 242, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "pos", "endpos", "startpos", "newitem", "childpos", "rightpos"};
      _siftup$12 = Py.newCode(2, var2, var1, "_siftup", 295, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "startpos", "pos", "newitem", "parentpos", "parent"};
      _siftdown_max$13 = Py.newCode(3, var2, var1, "_siftdown_max", 315, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"heap", "pos", "endpos", "startpos", "newitem", "childpos", "rightpos"};
      _siftup_max$14 = Py.newCode(2, var2, var1, "_siftup_max", 330, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterables", "_heappop", "_heapreplace", "_StopIteration", "h", "h_append", "itnum", "it", "next", "v", "s"};
      merge$15 = Py.newCode(1, var2, var1, "merge", 357, true, false, self, 15, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"n", "iterable", "key", "it", "head", "size", "result", "in1", "in2"};
      nsmallest$16 = Py.newCode(3, var2, var1, "nsmallest", 394, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "iterable", "key", "it", "head", "size", "result", "in1", "in2"};
      nlargest$17 = Py.newCode(3, var2, var1, "nlargest", 431, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new heapq$py("heapq$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(heapq$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.cmp_lt$1(var2, var3);
         case 2:
            return this.heappush$2(var2, var3);
         case 3:
            return this.heappop$3(var2, var3);
         case 4:
            return this.heapreplace$4(var2, var3);
         case 5:
            return this.heappushpop$5(var2, var3);
         case 6:
            return this.heapify$6(var2, var3);
         case 7:
            return this._heappushpop_max$7(var2, var3);
         case 8:
            return this._heapify_max$8(var2, var3);
         case 9:
            return this.nlargest$9(var2, var3);
         case 10:
            return this.nsmallest$10(var2, var3);
         case 11:
            return this._siftdown$11(var2, var3);
         case 12:
            return this._siftup$12(var2, var3);
         case 13:
            return this._siftdown_max$13(var2, var3);
         case 14:
            return this._siftup_max$14(var2, var3);
         case 15:
            return this.merge$15(var2, var3);
         case 16:
            return this.nsmallest$16(var2, var3);
         case 17:
            return this.nlargest$17(var2, var3);
         default:
            return null;
      }
   }
}
