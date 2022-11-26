package org.python.core;

import java.util.StringTokenizer;
import org.python.core.packagecache.PackageManager;

public class PyJavaPackage extends PyObject implements Traverseproc {
   public String __name__;
   public PyStringMap __dict__;
   public PyStringMap clsSet;
   public String __file__;
   public PackageManager __mgr__;

   public PyJavaPackage(String name) {
      this(name, (PackageManager)null, (String)null);
   }

   public PyJavaPackage(String name, String jarfile) {
      this(name, (PackageManager)null, jarfile);
   }

   public PyJavaPackage(String name, PackageManager mgr) {
      this(name, mgr, (String)null);
   }

   public PyJavaPackage(String name, PackageManager mgr, String jarfile) {
      this.__file__ = jarfile;
      this.__name__ = name;
      if (mgr == null) {
         this.__mgr__ = PySystemState.packageManager;
      } else {
         this.__mgr__ = mgr;
      }

      this.clsSet = new PyStringMap();
      this.__dict__ = new PyStringMap();
      this.__dict__.__setitem__((String)"__name__", new PyString(this.__name__));
   }

   public PyJavaPackage addPackage(String name) {
      return this.addPackage(name, (String)null);
   }

   public PyJavaPackage addPackage(String name, String jarfile) {
      int dot = name.indexOf(46);
      String firstName = name;
      String lastName = null;
      if (dot != -1) {
         firstName = name.substring(0, dot);
         lastName = name.substring(dot + 1, name.length());
      }

      firstName = firstName.intern();
      PyJavaPackage p = (PyJavaPackage)this.__dict__.__finditem__(firstName);
      if (p == null) {
         String pname = this.__name__.length() == 0 ? firstName : this.__name__ + '.' + firstName;
         p = new PyJavaPackage(pname, this.__mgr__, jarfile);
         this.__dict__.__setitem__((String)firstName, p);
      } else if (jarfile == null || !jarfile.equals(p.__file__)) {
         p.__file__ = null;
      }

      return lastName != null ? p.addPackage(lastName, jarfile) : p;
   }

   public PyObject addClass(String name, Class c) {
      PyObject ret = Py.java2py(c);
      this.__dict__.__setitem__(name.intern(), ret);
      return ret;
   }

   public void addPlaceholders(String classes) {
      StringTokenizer tok = new StringTokenizer(classes, ",@");

      while(tok.hasMoreTokens()) {
         String p = tok.nextToken();
         String name = p.trim().intern();
         if (this.clsSet.__finditem__(name) == null) {
            this.clsSet.__setitem__((String)name, Py.One);
         }
      }

   }

   public PyObject __dir__() {
      return this.__mgr__.doDir(this, false, false);
   }

   public PyObject fillDir() {
      return this.__mgr__.doDir(this, true, false);
   }

   public PyObject __findattr_ex__(String name) {
      PyObject ret = this.__dict__.__finditem__(name);
      if (ret != null) {
         return ret;
      } else if (this.__mgr__.packageExists(this.__name__, name)) {
         this.__mgr__.notifyPackageImport(this.__name__, name);
         return this.addPackage(name);
      } else {
         Class c = this.__mgr__.findClass(this.__name__, name);
         if (c != null) {
            return this.addClass(name, c);
         } else if (name == "__name__") {
            return new PyString(this.__name__);
         } else if (name == "__dict__") {
            return this.__dict__;
         } else if (name == "__mgr__") {
            return Py.java2py(this.__mgr__);
         } else if (name == "__file__") {
            return (PyObject)(this.__file__ == null ? Py.None : Py.fileSystemEncode(this.__file__));
         } else {
            return null;
         }
      }
   }

   public void __setattr__(String attr, PyObject value) {
      if (attr == "__mgr__") {
         PackageManager newMgr = (PackageManager)Py.tojava(value, PackageManager.class);
         if (newMgr == null) {
            throw Py.TypeError("cannot set java package __mgr__ to None");
         } else {
            this.__mgr__ = newMgr;
         }
      } else if (attr == "__file__") {
         this.__file__ = Py.fileSystemDecode(value);
      } else {
         super.__setattr__(attr, value);
      }
   }

   public String toString() {
      return "<java package " + this.__name__ + " " + Py.idstr(this) + ">";
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = visit.visit(this.__dict__, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         retVal = visit.visit(this.clsSet, arg);
         return retVal != 0 ? retVal : visit.visit(this.__mgr__.topLevelPackage, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.__dict__ || ob == this.clsSet || ob == this.__mgr__.topLevelPackage);
   }
}
