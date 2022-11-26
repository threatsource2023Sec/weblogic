package org.python.modules.itertools;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyIterator;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.Visitproc;

public class itertools implements ClassDictInit {
   public static final PyString __doc__ = new PyString("Functional tools for creating and using iterators.\n\nInfinite iterators:\ncount([n]) --> n, n+1, n+2, ...\ncycle(p) --> p0, p1, ... plast, p0, p1, ...\nrepeat(elem [,n]) --> elem, elem, elem, ... endlessly or up to n times\n\nIterators terminating on the shortest input sequence:\nchain(p, q, ...) --> p0, p1, ... plast, q0, q1, ...\ncompress(data, selectors) --> (d[0] if s[0]), (d[1] if s[1]), ...\ndropwhile(pred, seq) --> seq[n], seq[n+1], starting when pred fails\ngroupby(iterable[, keyfunc]) --> sub-iterators grouped by value of keyfunc(v)\nifilter(pred, seq) --> elements of seq where pred(elem) is True\nifilterfalse(pred, seq) --> elements of seq where pred(elem) is False\nislice(seq, [start,] stop [, step]) --> elements from seq[start:stop:step]\nimap(fun, p, q, ...) --> fun(p0, q0), fun(p1, q1), ...\nstarmap(fun, seq) --> fun(*seq[0]), fun(*seq[1]), ...\ntee(it, n=2) --> (it1, it2 , ... itn) splits one iterator into n\ntakewhile(pred, seq) --> seq[0], seq[1], until pred fails\nizip(p, q, ...) --> (p[0], q[0]), (p[1], q[1]), ...\nizip_longest(p, q, ...) --> (p[0], q[0]), (p[1], q[1]), ...\n\nCombinatoric generators:\nproduct(p, q, ... [repeat=1]) --> cartesian product\npermutations(p[, r])\ncombinations(p, r)\ncombinations_with_replacement(p, r)");
   public static PyString __doc__tee = new PyString("tee(iterable, n=2) --> tuple of n independent iterators.");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("itertools"));
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"chain", chain.TYPE);
      dict.__setitem__((String)"combinations", combinations.TYPE);
      dict.__setitem__((String)"combinations_with_replacement", combinationsWithReplacement.TYPE);
      dict.__setitem__((String)"compress", compress.TYPE);
      dict.__setitem__((String)"cycle", cycle.TYPE);
      dict.__setitem__((String)"count", count.TYPE);
      dict.__setitem__((String)"dropwhile", dropwhile.TYPE);
      dict.__setitem__((String)"groupby", groupby.TYPE);
      dict.__setitem__((String)"imap", imap.TYPE);
      dict.__setitem__((String)"ifilter", ifilter.TYPE);
      dict.__setitem__((String)"ifilterfalse", ifilterfalse.TYPE);
      dict.__setitem__((String)"islice", islice.TYPE);
      dict.__setitem__((String)"izip", izip.TYPE);
      dict.__setitem__((String)"izip_longest", izipLongest.TYPE);
      dict.__setitem__((String)"permutations", permutations.TYPE);
      dict.__setitem__((String)"product", product.TYPE);
      dict.__setitem__((String)"repeat", repeat.TYPE);
      dict.__setitem__((String)"starmap", starmap.TYPE);
      dict.__setitem__((String)"takewhile", takewhile.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"initClassExceptions", (PyObject)null);
   }

   static int py2int(PyObject obj, int defaultValue, String msg) {
      if (obj instanceof PyNone) {
         return defaultValue;
      } else {
         try {
            int value = Py.py2int(obj);
            return value;
         } catch (PyException var5) {
            if (var5.match(Py.TypeError)) {
               throw Py.ValueError(msg);
            } else {
               throw var5;
            }
         }
      }
   }

   public static PyTuple tee(PyObject iterable, int n) {
      return new PyTuple(PyTeeIterator.makeTees(iterable, n));
   }

   public static PyTuple tee(PyObject iterable) {
      return tee(iterable, 2);
   }

   static PyTuple makeIndexedTuple(PyTuple pool, int[] indices) {
      return makeIndexedTuple(pool, indices, indices.length);
   }

   static PyTuple makeIndexedTuple(PyTuple pool, int[] indices, int end) {
      PyObject[] items = new PyObject[end];

      for(int i = 0; i < end; ++i) {
         items[i] = pool.__getitem__(indices[i]);
      }

      return new PyTuple(items);
   }

   static class WhileIterator extends ItertoolsIterator {
      private PyObject iterator;
      private PyObject predicate;
      private boolean drop;
      private boolean predicateSatisfied;

      WhileIterator(PyObject predicate, PyObject iterable, boolean drop) {
         this.predicate = predicate;
         this.iterator = iterable.__iter__();
         this.drop = drop;
      }

      public PyObject __iternext__() {
         while(true) {
            PyObject element = this.nextElement(this.iterator);
            if (element != null) {
               if (!this.predicateSatisfied) {
                  if (this.predicate.__call__(element).__nonzero__() != this.drop) {
                     this.predicateSatisfied = this.drop;
                     return element;
                  }

                  this.predicateSatisfied = !this.drop;
                  continue;
               }

               if (this.drop) {
                  return element;
               }

               return null;
            }

            return null;
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         int retVal = super.traverse(visit, arg);
         if (retVal != 0) {
            return retVal;
         } else {
            if (this.iterator != null) {
               retVal = visit.visit(this.iterator, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }

            return this.predicate != null ? visit.visit(this.predicate, arg) : 0;
         }
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.iterator || ob == this.predicate || super.refersDirectlyTo(ob));
      }
   }

   static class FilterIterator extends ItertoolsIterator {
      private PyObject predicate;
      private PyObject iterator;
      private boolean filterTrue;

      FilterIterator(PyObject predicate, PyObject iterable, boolean filterTrue) {
         if (predicate instanceof PyNone) {
            this.predicate = null;
         } else {
            this.predicate = predicate;
         }

         this.iterator = iterable.__iter__();
         this.filterTrue = filterTrue;
      }

      public PyObject __iternext__() {
         while(true) {
            PyObject element = this.nextElement(this.iterator);
            if (element != null) {
               boolean booleanValue = this.predicate != null ? this.predicate.__call__(element).__nonzero__() : element.__nonzero__();
               if (booleanValue != this.filterTrue) {
                  continue;
               }

               return element;
            }

            return null;
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         int retVal = super.traverse(visit, arg);
         if (retVal != 0) {
            return retVal;
         } else {
            if (this.iterator != null) {
               retVal = visit.visit(this.iterator, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }

            return this.predicate != null ? visit.visit(this.predicate, arg) : 0;
         }
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.iterator || ob == this.predicate || super.refersDirectlyTo(ob));
      }
   }

   abstract static class ItertoolsIterator extends PyIterator {
      protected PyObject nextElement(PyObject pyIter) {
         PyObject element = null;

         try {
            element = pyIter.__iternext__();
         } catch (PyException var4) {
            if (!var4.match(Py.StopIteration)) {
               throw var4;
            }

            this.stopException = var4;
         }

         return element;
      }
   }
}
