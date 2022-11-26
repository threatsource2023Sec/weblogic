package org.python.modules._collections;

import java.util.Iterator;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.ThreadState;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "collections.deque"
)
public class PyDeque extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   private long state;
   private int size;
   private int maxlen;
   private Node header;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;

   public PyDeque() {
      this(TYPE);
   }

   public PyDeque(PyType subType) {
      super(subType);
      this.state = 0L;
      this.size = 0;
      this.maxlen = -1;
      this.header = new Node((PyObject)null, (Node)null, (Node)null);
      this.header.left = this.header.right = this.header;
   }

   @ExposedNew
   public final synchronized void deque___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("deque", args, kwds, new String[]{"iterable", "maxlen"}, 0);
      PyObject maxlenobj = ap.getPyObject(1, (PyObject)null);
      if (maxlenobj != null) {
         if (maxlenobj == Py.None) {
            this.maxlen = -1;
         } else {
            this.maxlen = ap.getInt(1);
            if (this.maxlen < 0) {
               throw Py.ValueError("maxlen must be non-negative");
            }
         }
      } else {
         this.maxlen = -1;
      }

      PyObject iterable = ap.getPyObject(0, (PyObject)null);
      if (iterable != null) {
         if (this.size != 0) {
            this.deque_clear();
         }

         this.deque_extend(iterable);
      }

   }

   public PyObject getMaxlen() {
      return (PyObject)(this.maxlen < 0 ? Py.None : Py.newInteger(this.maxlen));
   }

   public void setMaxlen(PyObject o) {
      throw Py.AttributeError("attribute 'maxlen' of 'collections.deque' objects is not writable");
   }

   public final synchronized void deque_append(PyObject obj) {
      if (this.maxlen >= 0) {
         if (!$assertionsDisabled && this.size > this.maxlen) {
            throw new AssertionError();
         }

         if (this.maxlen == 0) {
            return;
         }

         if (this.size == this.maxlen) {
            this.deque_popleft();
         }
      }

      this.addBefore(obj, this.header);
   }

   public final synchronized void deque_appendleft(PyObject obj) {
      if (this.maxlen >= 0) {
         if (!$assertionsDisabled && this.size > this.maxlen) {
            throw new AssertionError();
         }

         if (this.maxlen == 0) {
            return;
         }

         if (this.size == this.maxlen) {
            this.deque_pop();
         }
      }

      this.addBefore(obj, this.header.right);
   }

   private Node addBefore(PyObject obj, Node node) {
      Node newNode = new Node(obj, node, node.left);
      newNode.left.right = newNode;
      newNode.right.left = newNode;
      ++this.size;
      ++this.state;
      return newNode;
   }

   public final synchronized void deque_clear() {
      for(Node node = this.header.right; node != this.header; ++this.state) {
         Node right = node.right;
         node.left = null;
         node.right = null;
         node.data = null;
         node = right;
      }

      this.header.right = this.header.left = this.header;
      this.size = 0;
   }

   public final synchronized void deque_extend(PyObject iterable) {
      if (this == iterable) {
         this.deque_extend(new PyList(iterable));
      } else {
         Iterator var2 = iterable.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject item = (PyObject)var2.next();
            this.deque_append(item);
         }
      }

   }

   public final synchronized void deque_extendleft(PyObject iterable) {
      if (this == iterable) {
         this.deque_extendleft(new PyList(iterable));
      } else {
         Iterator var2 = iterable.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject item = (PyObject)var2.next();
            this.deque_appendleft(item);
         }
      }

   }

   public final synchronized PyObject deque_pop() {
      return this.removeNode(this.header.left);
   }

   public final synchronized PyObject deque_popleft() {
      return this.removeNode(this.header.right);
   }

   private PyObject removeNode(Node node) {
      if (node == this.header) {
         throw Py.IndexError("pop from an empty deque");
      } else {
         PyObject obj = node.data;
         node.left.right = node.right;
         node.right.left = node.left;
         node.right = null;
         node.left = null;
         node.data = null;
         --this.size;
         ++this.state;
         return obj;
      }
   }

   public final synchronized PyObject deque_remove(PyObject value) {
      int n = this.size;
      Node tmp = this.header.right;
      boolean match = false;
      long startState = this.state;

      for(int i = 0; i < n; ++i) {
         if (tmp.data.equals(value)) {
            match = true;
         }

         if (startState != this.state) {
            throw Py.IndexError("deque mutated during remove().");
         }

         if (match) {
            return this.removeNode(tmp);
         }

         tmp = tmp.right;
      }

      throw Py.ValueError("deque.remove(x): x not in deque");
   }

   public final synchronized PyObject deque_count(PyObject x) {
      int n = this.size;
      int count = 0;
      Node tmp = this.header.right;
      long startState = this.state;

      for(int i = 0; i < n; ++i) {
         if (tmp.data.equals(x)) {
            ++count;
         }

         if (startState != this.state) {
            throw Py.RuntimeError("deque mutated during count().");
         }

         tmp = tmp.right;
      }

      return Py.newInteger(count);
   }

   public final synchronized void deque_rotate(int steps) {
      if (this.size != 0) {
         int halfsize = this.size + 1 >> 1;
         if (steps > halfsize || steps < -halfsize) {
            steps %= this.size;
            if (steps > halfsize) {
               steps -= this.size;
            } else if (steps < -halfsize) {
               steps += this.size;
            }
         }

         int i;
         for(i = 0; i < steps; ++i) {
            this.deque_appendleft(this.deque_pop());
         }

         for(i = 0; i > steps; --i) {
            this.deque_append(this.deque_popleft());
         }

      }
   }

   public final synchronized PyObject deque_reverse() {
      Node headerRight = this.header.right;
      Node headerLeft = this.header.left;

      Node right;
      for(Node node = this.header.right; node != this.header; node = right) {
         right = node.right;
         Node left = node.left;
         node.right = left;
         node.left = right;
      }

      this.header.right = headerLeft;
      this.header.left = headerRight;
      ++this.state;
      return Py.None;
   }

   public String toString() {
      return this.deque_toString();
   }

   final synchronized String deque_toString() {
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return "[...]";
      } else {
         long startState = this.state;
         StringBuilder buf = (new StringBuilder("deque")).append("([");

         for(Node tmp = this.header.right; tmp != this.header; tmp = tmp.right) {
            buf.append(tmp.data.__repr__().toString());
            if (startState != this.state) {
               throw Py.RuntimeError("deque mutated during iteration.");
            }

            if (tmp.right != this.header) {
               buf.append(", ");
            }
         }

         buf.append("]");
         if (this.maxlen >= 0) {
            buf.append(", maxlen=");
            buf.append(this.maxlen);
         }

         buf.append(")");
         ts.exitRepr(this);
         return buf.toString();
      }
   }

   public int __len__() {
      return this.deque___len__();
   }

   final synchronized int deque___len__() {
      return this.size;
   }

   public boolean __nonzero__() {
      return this.deque___nonzero__();
   }

   final synchronized boolean deque___nonzero__() {
      return this.size != 0;
   }

   public PyObject __finditem__(PyObject key) {
      try {
         return this.deque___getitem__(key);
      } catch (PyException var3) {
         if (var3.match(Py.KeyError)) {
            return null;
         } else {
            throw var3;
         }
      }
   }

   final synchronized PyObject deque___getitem__(PyObject index) {
      return this.getNode(index).data;
   }

   public void __setitem__(PyObject index, PyObject value) {
      this.deque___setitem__(index, value);
   }

   final synchronized void deque___setitem__(PyObject index, PyObject value) {
      Node node = this.getNode(index).right;
      this.removeNode(node.left);
      this.addBefore(value, node);
   }

   public void __delitem__(PyObject key) {
      this.deque___delitem__(key);
   }

   final synchronized void deque___delitem__(PyObject key) {
      this.removeNode(this.getNode(key));
   }

   private Node getNode(PyObject index) {
      int pos = false;
      if (!index.isIndex()) {
         throw Py.TypeError(String.format("sequence index must be integer, not '%.200s'", index.getType().fastGetName()));
      } else {
         int pos = index.asIndex(Py.IndexError);
         if (pos < 0) {
            pos += this.size;
         }

         if (pos >= 0 && pos < this.size) {
            Node tmp = this.header;
            int i;
            if (pos < this.size >> 1) {
               for(i = 0; i <= pos; ++i) {
                  tmp = tmp.right;
               }
            } else {
               for(i = this.size - 1; i >= pos; --i) {
                  tmp = tmp.left;
               }
            }

            return tmp;
         } else {
            throw Py.IndexError("index out of range: " + index);
         }
      }
   }

   public PyObject __iter__() {
      return this.deque___iter__();
   }

   final PyObject deque___iter__() {
      return new PyDequeIter();
   }

   public synchronized PyObject __eq__(PyObject o) {
      return this.deque___eq__(o);
   }

   final synchronized PyObject deque___eq__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int tl = this.__len__();
         int ol = o.__len__();
         if (tl != ol) {
            return Py.False;
         } else {
            int i = cmp(this, tl, o, ol);
            return i < 0 ? Py.True : Py.False;
         }
      }
   }

   public synchronized PyObject __ne__(PyObject o) {
      return this.deque___ne__(o);
   }

   final synchronized PyObject deque___ne__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int tl = this.__len__();
         int ol = o.__len__();
         if (tl != ol) {
            return Py.True;
         } else {
            int i = cmp(this, tl, o, ol);
            return i < 0 ? Py.False : Py.True;
         }
      }
   }

   public synchronized PyObject __lt__(PyObject o) {
      return this.deque___lt__(o);
   }

   final synchronized PyObject deque___lt__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int i = cmp(this, -1, o, -1);
         if (i < 0) {
            return i == -1 ? Py.True : Py.False;
         } else {
            return this.__finditem__(i)._lt(o.__finditem__(i));
         }
      }
   }

   public synchronized PyObject __le__(PyObject o) {
      return this.deque___le__(o);
   }

   final synchronized PyObject deque___le__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int i = cmp(this, -1, o, -1);
         if (i >= 0) {
            return this.__finditem__(i)._le(o.__finditem__(i));
         } else {
            return i != -1 && i != -2 ? Py.False : Py.True;
         }
      }
   }

   public synchronized PyObject __gt__(PyObject o) {
      return this.deque___gt__(o);
   }

   final synchronized PyObject deque___gt__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int i = cmp(this, -1, o, -1);
         if (i < 0) {
            return i == -3 ? Py.True : Py.False;
         } else {
            return this.__finditem__(i)._gt(o.__finditem__(i));
         }
      }
   }

   public synchronized PyObject __ge__(PyObject o) {
      return this.deque___ge__(o);
   }

   final synchronized PyObject deque___ge__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int i = cmp(this, -1, o, -1);
         if (i >= 0) {
            return this.__finditem__(i)._ge(o.__finditem__(i));
         } else {
            return i != -3 && i != -2 ? Py.False : Py.True;
         }
      }
   }

   public synchronized PyObject __iadd__(PyObject o) {
      return this.deque___iadd__(o);
   }

   final synchronized PyObject deque___iadd__(PyObject o) {
      this.deque_extend(o);
      return this;
   }

   protected static int cmp(PyObject o1, int ol1, PyObject o2, int ol2) {
      if (ol1 < 0) {
         ol1 = o1.__len__();
      }

      if (ol2 < 0) {
         ol2 = o2.__len__();
      }

      for(int i = 0; i < ol1 && i < ol2; ++i) {
         if (!o1.__getitem__(i).equals(o2.__getitem__(i))) {
            return i;
         }
      }

      if (ol1 == ol2) {
         return -2;
      } else {
         return ol1 < ol2 ? -1 : -3;
      }
   }

   public int hashCode() {
      return this.deque_hashCode();
   }

   final int deque_hashCode() {
      throw Py.TypeError("deque objects are unhashable");
   }

   public PyObject __reduce__() {
      return this.deque___reduce__();
   }

   final PyObject deque___reduce__() {
      PyObject dict = this.getDict();
      if (dict == null) {
         dict = Py.None;
      }

      return new PyTuple(new PyObject[]{this.getType(), Py.EmptyTuple, dict, this.__iter__()});
   }

   final PyObject deque___copy__() {
      PyDeque pd = (PyDeque)this.getType().__call__();
      pd.deque_extend(this);
      return pd;
   }

   public boolean isMappingType() {
      return false;
   }

   public boolean isSequenceType() {
      return true;
   }

   public synchronized int traverse(Visitproc visit, Object arg) {
      if (this.header == null) {
         return 0;
      } else {
         int retVal = 0;
         if (this.header.data != null) {
            retVal = visit.visit(this.header.data, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         for(Node tmp = this.header.right; tmp != this.header; tmp = tmp.right) {
            if (tmp.data != null) {
               retVal = visit.visit(tmp.data, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }

         return retVal;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   static {
      PyType.addBuilder(PyDeque.class, new PyExposer());
      $assertionsDisabled = !PyDeque.class.desiredAssertionStatus();
      TYPE = PyType.fromClass(PyDeque.class);
   }

   private class PyDequeIter extends PyIterator {
      private Node lastReturned;
      private long startState;

      public PyDequeIter() {
         this.lastReturned = PyDeque.this.header;
         this.startState = PyDeque.this.state;
      }

      public PyObject __iternext__() {
         synchronized(PyDeque.this) {
            if (this.startState != PyDeque.this.state) {
               throw Py.RuntimeError("deque changed size during iteration");
            } else if (this.lastReturned.right != PyDeque.this.header) {
               this.lastReturned = this.lastReturned.right;
               return this.lastReturned.data;
            } else {
               return null;
            }
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         int retVal = super.traverse(visit, arg);
         return retVal != 0 ? retVal : PyDeque.this.traverse(visit, arg);
      }

      public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
         if (ob == null) {
            return false;
         } else if (super.refersDirectlyTo(ob)) {
            return true;
         } else {
            throw new UnsupportedOperationException();
         }
      }
   }

   private static class Node {
      private Node left;
      private Node right;
      private PyObject data;

      Node(PyObject data, Node right, Node left) {
         this.data = data;
         this.right = right;
         this.left = left;
      }
   }

   private static class deque___init___exposer extends PyBuiltinMethod {
      public deque___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public deque___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyDeque)this.self).deque___init__(var1, var2);
         return Py.None;
      }
   }

   private static class deque_append_exposer extends PyBuiltinMethodNarrow {
      public deque_append_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_append_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_append_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque_append(var1);
         return Py.None;
      }
   }

   private static class deque_appendleft_exposer extends PyBuiltinMethodNarrow {
      public deque_appendleft_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_appendleft_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_appendleft_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque_appendleft(var1);
         return Py.None;
      }
   }

   private static class deque_clear_exposer extends PyBuiltinMethodNarrow {
      public deque_clear_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_clear_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_clear_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyDeque)this.self).deque_clear();
         return Py.None;
      }
   }

   private static class deque_extend_exposer extends PyBuiltinMethodNarrow {
      public deque_extend_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_extend_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_extend_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque_extend(var1);
         return Py.None;
      }
   }

   private static class deque_extendleft_exposer extends PyBuiltinMethodNarrow {
      public deque_extendleft_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_extendleft_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_extendleft_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque_extendleft(var1);
         return Py.None;
      }
   }

   private static class deque_pop_exposer extends PyBuiltinMethodNarrow {
      public deque_pop_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque_pop();
      }
   }

   private static class deque_popleft_exposer extends PyBuiltinMethodNarrow {
      public deque_popleft_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_popleft_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_popleft_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque_popleft();
      }
   }

   private static class deque_remove_exposer extends PyBuiltinMethodNarrow {
      public deque_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDeque)this.self).deque_remove(var1);
      }
   }

   private static class deque_count_exposer extends PyBuiltinMethodNarrow {
      public deque_count_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDeque)this.self).deque_count(var1);
      }
   }

   private static class deque_rotate_exposer extends PyBuiltinMethodNarrow {
      public deque_rotate_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public deque_rotate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_rotate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque_rotate(Py.py2int(var1));
         return Py.None;
      }

      public PyObject __call__() {
         ((PyDeque)this.self).deque_rotate(1);
         return Py.None;
      }
   }

   private static class deque_reverse_exposer extends PyBuiltinMethodNarrow {
      public deque_reverse_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_reverse_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_reverse_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque_reverse();
      }
   }

   private static class deque_toString_exposer extends PyBuiltinMethodNarrow {
      public deque_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyDeque)this.self).deque_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class deque___len___exposer extends PyBuiltinMethodNarrow {
      public deque___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyDeque)this.self).deque___len__());
      }
   }

   private static class deque___nonzero___exposer extends PyBuiltinMethodNarrow {
      public deque___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyDeque)this.self).deque___nonzero__());
      }
   }

   private static class deque___getitem___exposer extends PyBuiltinMethodNarrow {
      public deque___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDeque)this.self).deque___getitem__(var1);
      }
   }

   private static class deque___setitem___exposer extends PyBuiltinMethodNarrow {
      public deque___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public deque___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyDeque)this.self).deque___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class deque___delitem___exposer extends PyBuiltinMethodNarrow {
      public deque___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDeque)this.self).deque___delitem__(var1);
         return Py.None;
      }
   }

   private static class deque___iter___exposer extends PyBuiltinMethodNarrow {
      public deque___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque___iter__();
      }
   }

   private static class deque___eq___exposer extends PyBuiltinMethodNarrow {
      public deque___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___ne___exposer extends PyBuiltinMethodNarrow {
      public deque___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___lt___exposer extends PyBuiltinMethodNarrow {
      public deque___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___le___exposer extends PyBuiltinMethodNarrow {
      public deque___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___gt___exposer extends PyBuiltinMethodNarrow {
      public deque___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___ge___exposer extends PyBuiltinMethodNarrow {
      public deque___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque___iadd___exposer extends PyBuiltinMethodNarrow {
      public deque___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public deque___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDeque)this.self).deque___iadd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class deque_hashCode_exposer extends PyBuiltinMethodNarrow {
      public deque_hashCode_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque_hashCode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque_hashCode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyDeque)this.self).deque_hashCode());
      }
   }

   private static class deque___reduce___exposer extends PyBuiltinMethodNarrow {
      public deque___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque___reduce__();
      }
   }

   private static class deque___copy___exposer extends PyBuiltinMethodNarrow {
      public deque___copy___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public deque___copy___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new deque___copy___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDeque)this.self).deque___copy__();
      }
   }

   private static class maxlen_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public maxlen_descriptor() {
         super("maxlen", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDeque)var1).getMaxlen();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyDeque)var1).setMaxlen((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyDeque var4 = new PyDeque(this.for_type);
         if (var1) {
            var4.deque___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyDequeDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new deque___init___exposer("__init__"), new deque_append_exposer("append"), new deque_appendleft_exposer("appendleft"), new deque_clear_exposer("clear"), new deque_extend_exposer("extend"), new deque_extendleft_exposer("extendleft"), new deque_pop_exposer("pop"), new deque_popleft_exposer("popleft"), new deque_remove_exposer("remove"), new deque_count_exposer("count"), new deque_rotate_exposer("rotate"), new deque_reverse_exposer("reverse"), new deque_toString_exposer("__repr__"), new deque___len___exposer("__len__"), new deque___nonzero___exposer("__nonzero__"), new deque___getitem___exposer("__getitem__"), new deque___setitem___exposer("__setitem__"), new deque___delitem___exposer("__delitem__"), new deque___iter___exposer("__iter__"), new deque___eq___exposer("__eq__"), new deque___ne___exposer("__ne__"), new deque___lt___exposer("__lt__"), new deque___le___exposer("__le__"), new deque___gt___exposer("__gt__"), new deque___ge___exposer("__ge__"), new deque___iadd___exposer("__iadd__"), new deque_hashCode_exposer("__hash__"), new deque___reduce___exposer("__reduce__"), new deque___copy___exposer("__copy__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new maxlen_descriptor()};
         super("collections.deque", PyDeque.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
