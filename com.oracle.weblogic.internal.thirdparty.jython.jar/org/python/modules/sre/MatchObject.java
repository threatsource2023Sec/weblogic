package org.python.modules.sre;

import java.math.BigInteger;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.imp;

public class MatchObject extends PyObject implements Traverseproc {
   public PyString string;
   public PyObject regs;
   PatternObject pattern;
   int pos;
   int endpos;
   int lastindex;
   int groups;
   int[] mark;

   public PyObject expand(PyObject[] args) {
      if (args.length == 0) {
         throw Py.TypeError("expand() takes exactly 1 argument (0 given)");
      } else {
         PyObject mod = imp.importName("re", true);
         PyObject func = mod.__getattr__("_expand");
         return func.__call__(new PyObject[]{this.pattern, this, args[0]});
      }
   }

   public PyObject group(PyObject[] args) {
      switch (args.length) {
         case 0:
            return this.getslice(Py.Zero, Py.None);
         case 1:
            return this.getslice(args[0], Py.None);
         default:
            PyObject[] result = new PyObject[args.length];

            for(int i = 0; i < args.length; ++i) {
               result[i] = this.getslice(args[i], Py.None);
            }

            return new PyTuple(result);
      }
   }

   public PyObject groups(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("groups", args, kws, "default");
      PyObject def = ap.getPyObject(0, Py.None);
      PyObject[] result = new PyObject[this.groups - 1];

      for(int i = 1; i < this.groups; ++i) {
         result[i - 1] = this.getslice_by_index(i, def);
      }

      return new PyTuple(result);
   }

   public PyObject groupdict(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("groupdict", args, kws, "default");
      PyObject def = ap.getPyObject(0, Py.None);
      PyObject result = new PyDictionary();
      if (this.pattern.groupindex == null) {
         return result;
      } else {
         PyObject keys = this.pattern.groupindex.invoke("keys");

         PyObject key;
         for(int i = 0; (key = keys.__finditem__(i)) != null; ++i) {
            PyObject item = this.getslice(key, def);
            result.__setitem__(key, item);
         }

         return result;
      }
   }

   public PyObject start() {
      return this.start(Py.Zero);
   }

   public PyObject start(PyObject index_) {
      int index = this.getindex(index_);
      if (index >= 0 && index < this.groups) {
         return Py.newInteger(this.mark[index * 2]);
      } else {
         throw Py.IndexError("no such group");
      }
   }

   public PyObject end() {
      return this.end(Py.Zero);
   }

   public PyObject end(PyObject index_) {
      int index = this.getindex(index_);
      if (index >= 0 && index < this.groups) {
         return Py.newInteger(this.mark[index * 2 + 1]);
      } else {
         throw Py.IndexError("no such group");
      }
   }

   public PyTuple span() {
      return this.span(Py.Zero);
   }

   public PyTuple span(PyObject index_) {
      int index = this.getindex(index_);
      if (index >= 0 && index < this.groups) {
         int start = this.mark[index * 2];
         int end = this.mark[index * 2 + 1];
         return this._pair(start, end);
      } else {
         throw Py.IndexError("no such group");
      }
   }

   public PyObject regs() {
      PyObject[] regs = new PyObject[this.groups];

      for(int index = 0; index < this.groups; ++index) {
         regs[index] = this._pair(this.mark[index * 2], this.mark[index * 2 + 1]);
      }

      return new PyTuple(regs);
   }

   PyTuple _pair(int i1, int i2) {
      return new PyTuple(new PyObject[]{Py.newInteger(i1), Py.newInteger(i2)});
   }

   private PyObject getslice(PyObject index, PyObject def) {
      return this.getslice_by_index(this.getindex(index), def);
   }

   private int getindex(PyObject index) {
      if (index instanceof PyInteger) {
         return ((PyInteger)index).getValue();
      } else if (index instanceof PyLong) {
         BigInteger idx = ((PyLong)index).getValue();
         if (idx.compareTo(PyInteger.MAX_INT) == 1) {
            throw Py.IndexError("no such group");
         } else {
            return idx.intValue();
         }
      } else {
         int i = -1;
         if (this.pattern.groupindex != null) {
            index = this.pattern.groupindex.__finditem__(index);
            if (index != null && index instanceof PyInteger) {
               return ((PyInteger)index).getValue();
            }
         }

         return i;
      }
   }

   private PyObject getslice_by_index(int index, PyObject def) {
      if (index >= 0 && index < this.groups) {
         index *= 2;
         int start = this.mark[index];
         int end = this.mark[index + 1];
         return this.string != null && start >= 0 ? this.string.__getslice__(Py.newInteger(start), Py.newInteger(end)) : def;
      } else {
         throw Py.IndexError("no such group");
      }
   }

   public PyObject __findattr_ex__(String key) {
      if (key == "flags") {
         return Py.newInteger(this.pattern.flags);
      } else if (key == "groupindex") {
         return this.pattern.groupindex;
      } else if (key == "re") {
         return this.pattern;
      } else if (key == "pos") {
         return Py.newInteger(this.pos);
      } else if (key == "endpos") {
         return Py.newInteger(this.endpos);
      } else if (key == "lastindex") {
         return (PyObject)(this.lastindex == -1 ? Py.None : Py.newInteger(this.lastindex));
      } else if (key == "lastgroup") {
         return this.pattern.indexgroup != null && this.lastindex >= 0 ? this.pattern.indexgroup.__getitem__(this.lastindex) : Py.None;
      } else {
         return key == "regs" ? this.regs() : super.__findattr_ex__(key);
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.pattern != null) {
         retVal = visit.visit(this.pattern, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.string != null) {
         retVal = visit.visit(this.string, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.regs != null ? visit.visit(this.regs, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.pattern || ob == this.string || ob == this.regs);
   }
}
