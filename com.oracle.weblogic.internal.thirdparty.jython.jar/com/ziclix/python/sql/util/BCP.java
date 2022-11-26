package com.ziclix.python.sql.util;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.pipe.Pipe;
import com.ziclix.python.sql.pipe.db.DBSink;
import com.ziclix.python.sql.pipe.db.DBSource;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class BCP extends PyObject implements ClassDictInit, Traverseproc {
   protected Class sourceDH;
   protected Class destDH;
   protected int batchsize;
   protected int queuesize;
   protected PyConnection source;
   protected PyConnection destination;
   protected static PyList __methods__;
   protected static PyList __members__;

   public BCP(PyConnection source, PyConnection destination) {
      this(source, destination, -1);
   }

   public BCP(PyConnection source, PyConnection destination, int batchsize) {
      this.source = source;
      this.destination = destination;
      this.destDH = null;
      this.sourceDH = null;
      this.batchsize = batchsize;
      this.queuesize = 0;
   }

   public String toString() {
      return "<BCP object instance at " + this.hashCode() + ">";
   }

   public void __setattr__(String name, PyObject value) {
      if ("destinationDataHandler".equals(name)) {
         this.destDH = (Class)value.__tojava__(Class.class);
      } else if ("sourceDataHandler".equals(name)) {
         this.sourceDH = (Class)value.__tojava__(Class.class);
      } else if ("batchsize".equals(name)) {
         this.batchsize = ((Number)value.__tojava__(Number.class)).intValue();
      } else if ("queuesize".equals(name)) {
         this.queuesize = ((Number)value.__tojava__(Number.class)).intValue();
      } else {
         super.__setattr__(name, value);
      }

   }

   public PyObject __findattr_ex__(String name) {
      if ("destinationDataHandler".equals(name)) {
         return Py.java2py(this.destDH);
      } else if ("sourceDataHandler".equals(name)) {
         return Py.java2py(this.sourceDH);
      } else if ("batchsize".equals(name)) {
         return Py.newInteger(this.batchsize);
      } else {
         return (PyObject)("queuesize".equals(name) ? Py.newInteger(this.queuesize) : super.__findattr_ex__(name));
      }
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"bcp", new BCPFunc("bcp", 0, 1, 2, zxJDBC.getString("bcp")));
      dict.__setitem__((String)"batchsize", Py.newString(zxJDBC.getString("batchsize")));
      dict.__setitem__((String)"queuesize", Py.newString(zxJDBC.getString("queuesize")));
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"toString", (PyObject)null);
      dict.__setitem__((String)"PyClass", (PyObject)null);
      dict.__setitem__((String)"getPyClass", (PyObject)null);
      dict.__setitem__((String)"sourceDH", (PyObject)null);
      dict.__setitem__((String)"destDH", (PyObject)null);
   }

   protected PyObject bcp(String fromTable, String where, PyObject params, PyObject include, PyObject exclude, String toTable, PyObject bindings) {
      Pipe pipe = new Pipe();
      String _toTable = toTable == null ? fromTable : toTable;
      DBSource source = new DBSource(this.source, this.sourceDH, fromTable, where, include, params);
      DBSink sink = new DBSink(this.destination, this.destDH, _toTable, exclude, bindings, this.batchsize);
      return pipe.pipe(source, sink).__sub__(Py.newInteger(1));
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.source != null) {
         int retVal = visit.visit(this.source, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.destination != null ? visit.visit(this.destination, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.source || ob == this.destination);
   }

   static {
      PyObject[] m = new PyObject[]{new PyString("bcp")};
      __methods__ = new PyList(m);
      m = new PyObject[]{new PyString("source"), new PyString("destination"), new PyString("batchsize"), new PyString("queuesize"), new PyString("sourceDataHandler"), new PyString("destinationDataHandler")};
      __members__ = new PyList(m);
   }
}
