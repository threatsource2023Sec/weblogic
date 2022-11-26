package org.python.antlr.ast;

import java.io.Serializable;
import org.python.core.AbstractDict;
import org.python.core.Deriveds;
import org.python.core.JyAttribute;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.core.PyComplex;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PySequenceIter;
import org.python.core.PySlice;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Slotted;
import org.python.core.TraverseprocDerived;
import org.python.core.Visitproc;
import org.python.core.finalization.FinalizablePyObjectDerived;
import org.python.core.finalization.FinalizeTrigger;

public class IfExpDerived extends IfExp implements Slotted, FinalizablePyObjectDerived, TraverseprocDerived {
   private PyObject[] slots;
   private PyObject dict;

   public PyObject getSlot(int index) {
      return this.slots[index];
   }

   public void setSlot(int index, PyObject value) {
      this.slots[index] = value;
   }

   public void __del_derived__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__del__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__();
      }

   }

   public void __ensure_finalizer__() {
      FinalizeTrigger.ensureFinalizer(this);
   }

   public int traverseDerived(Visitproc visit, Object arg) {
      int retVal;
      for(int i = 0; i < this.slots.length; ++i) {
         if (this.slots[i] != null) {
            retVal = visit.visit(this.slots[i], arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      retVal = visit.visit(this.objtype, arg);
      return retVal != 0 ? retVal : this.traverseDictIfAny(visit, arg);
   }

   public PyObject fastGetDict() {
      return this.dict;
   }

   public PyObject getDict() {
      return this.dict;
   }

   public void setDict(PyObject newDict) {
      if (newDict instanceof AbstractDict) {
         this.dict = newDict;
         if (this.dict.__finditem__((PyObject)PyString.fromInterned("__del__")) != null && !JyAttribute.hasAttr(this, (byte)127)) {
            FinalizeTrigger.ensureFinalizer(this);
         }

      } else {
         throw Py.TypeError("__dict__ must be set to a Dictionary " + newDict.getClass().getName());
      }
   }

   public void delDict() {
      this.dict = new PyStringMap();
   }

   public IfExpDerived(PyType subtype) {
      super(subtype);
      this.slots = new PyObject[subtype.getNumSlots()];
      this.dict = subtype.instDict();
      if (subtype.needsFinalizer()) {
         FinalizeTrigger.ensureFinalizer(this);
      }

   }

   public int traverseDictIfAny(Visitproc visit, Object arg) {
      return visit.visit(this.dict, arg);
   }

   public PyString __str__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__str__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyString) {
            return (PyString)res;
         } else {
            throw Py.TypeError("__str__ returned non-string (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__str__();
      }
   }

   public PyString __repr__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__repr__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyString) {
            return (PyString)res;
         } else {
            throw Py.TypeError("__repr__ returned non-string (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__repr__();
      }
   }

   public PyString __hex__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__hex__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyString) {
            return (PyString)res;
         } else {
            throw Py.TypeError("__hex__ returned non-string (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__hex__();
      }
   }

   public PyString __oct__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__oct__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyString) {
            return (PyString)res;
         } else {
            throw Py.TypeError("__oct__ returned non-string (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__oct__();
      }
   }

   public PyFloat __float__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__float__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyFloat) {
            return (PyFloat)res;
         } else {
            throw Py.TypeError("__float__ returned non-float (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__float__();
      }
   }

   public PyComplex __complex__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__complex__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyComplex) {
            return (PyComplex)res;
         } else {
            throw Py.TypeError("__complex__ returned non-complex (type " + res.getType().fastGetName() + ")");
         }
      } else {
         return super.__complex__();
      }
   }

   public PyObject __pos__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__pos__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__pos__();
   }

   public PyObject __neg__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__neg__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__neg__();
   }

   public PyObject __abs__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__abs__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__abs__();
   }

   public PyObject __invert__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__invert__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__invert__();
   }

   public PyObject __reduce__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__reduce__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__reduce__();
   }

   public PyObject __dir__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__dir__");
      return impl != null ? impl.__get__(this, self_type).__call__() : super.__dir__();
   }

   public PyObject __add__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__add__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__add__(other);
      }
   }

   public PyObject __radd__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__radd__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__radd__(other);
      }
   }

   public PyObject __sub__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__sub__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__sub__(other);
      }
   }

   public PyObject __rsub__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rsub__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rsub__(other);
      }
   }

   public PyObject __mul__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__mul__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__mul__(other);
      }
   }

   public PyObject __rmul__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rmul__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rmul__(other);
      }
   }

   public PyObject __div__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__div__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__div__(other);
      }
   }

   public PyObject __rdiv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rdiv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rdiv__(other);
      }
   }

   public PyObject __floordiv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__floordiv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__floordiv__(other);
      }
   }

   public PyObject __rfloordiv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rfloordiv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rfloordiv__(other);
      }
   }

   public PyObject __truediv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__truediv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__truediv__(other);
      }
   }

   public PyObject __rtruediv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rtruediv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rtruediv__(other);
      }
   }

   public PyObject __mod__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__mod__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__mod__(other);
      }
   }

   public PyObject __rmod__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rmod__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rmod__(other);
      }
   }

   public PyObject __divmod__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__divmod__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__divmod__(other);
      }
   }

   public PyObject __rdivmod__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rdivmod__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rdivmod__(other);
      }
   }

   public PyObject __rpow__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rpow__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rpow__(other);
      }
   }

   public PyObject __lshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__lshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__lshift__(other);
      }
   }

   public PyObject __rlshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rlshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rlshift__(other);
      }
   }

   public PyObject __rshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rshift__(other);
      }
   }

   public PyObject __rrshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rrshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rrshift__(other);
      }
   }

   public PyObject __and__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__and__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__and__(other);
      }
   }

   public PyObject __rand__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rand__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rand__(other);
      }
   }

   public PyObject __or__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__or__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__or__(other);
      }
   }

   public PyObject __ror__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ror__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ror__(other);
      }
   }

   public PyObject __xor__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__xor__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__xor__(other);
      }
   }

   public PyObject __rxor__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__rxor__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__rxor__(other);
      }
   }

   public PyObject __lt__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__lt__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__lt__(other);
      }
   }

   public PyObject __le__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__le__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__le__(other);
      }
   }

   public PyObject __gt__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__gt__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__gt__(other);
      }
   }

   public PyObject __ge__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ge__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ge__(other);
      }
   }

   public PyObject __eq__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__eq__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__eq__(other);
      }
   }

   public PyObject __ne__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ne__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ne__(other);
      }
   }

   public PyObject __format__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__format__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__format__(other);
      }
   }

   public PyObject __iadd__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__iadd__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__iadd__(other);
      }
   }

   public PyObject __isub__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__isub__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__isub__(other);
      }
   }

   public PyObject __imul__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__imul__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__imul__(other);
      }
   }

   public PyObject __idiv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__idiv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__idiv__(other);
      }
   }

   public PyObject __ifloordiv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ifloordiv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ifloordiv__(other);
      }
   }

   public PyObject __itruediv__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__itruediv__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__itruediv__(other);
      }
   }

   public PyObject __imod__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__imod__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__imod__(other);
      }
   }

   public PyObject __ipow__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ipow__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ipow__(other);
      }
   }

   public PyObject __ilshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ilshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ilshift__(other);
      }
   }

   public PyObject __irshift__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__irshift__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__irshift__(other);
      }
   }

   public PyObject __iand__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__iand__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__iand__(other);
      }
   }

   public PyObject __ior__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ior__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ior__(other);
      }
   }

   public PyObject __ixor__(PyObject other) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__ixor__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__ixor__(other);
      }
   }

   public PyObject __int__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__int__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (!(res instanceof PyInteger) && !(res instanceof PyLong)) {
            throw Py.TypeError("__int__ should return an integer");
         } else {
            return res;
         }
      } else {
         return super.__int__();
      }
   }

   public PyObject __long__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__long__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (!(res instanceof PyLong) && !(res instanceof PyInteger)) {
            throw Py.TypeError("__long__ returned non-long (type " + res.getType().fastGetName() + ")");
         } else {
            return res;
         }
      } else {
         return super.__long__();
      }
   }

   public int hashCode() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__hash__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyInteger) {
            return ((PyInteger)res).getValue();
         } else if (res instanceof PyLong) {
            return ((PyLong)res).getValue().intValue();
         } else {
            throw Py.TypeError("__hash__ should return a int");
         }
      } else if (self_type.lookup("__eq__") == null && self_type.lookup("__cmp__") == null) {
         return super.hashCode();
      } else {
         throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
      }
   }

   public PyUnicode __unicode__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__unicode__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (res instanceof PyUnicode) {
            return (PyUnicode)res;
         } else if (res instanceof PyString) {
            return new PyUnicode((PyString)res);
         } else {
            throw Py.TypeError("__unicode__ should return a unicode");
         }
      } else {
         return super.__unicode__();
      }
   }

   public int __cmp__(PyObject other) {
      PyType self_type = this.getType();
      PyObject[] where_type = new PyObject[1];
      PyObject impl = self_type.lookup_where("__cmp__", where_type);
      if (impl != null && where_type[0] != TYPE && !Py.isSubClass(TYPE, where_type[0])) {
         PyObject res = impl.__get__(this, self_type).__call__(other);
         if (res == Py.NotImplemented) {
            return -2;
         } else {
            int c = res.asInt();
            return c < 0 ? -1 : (c > 0 ? 1 : 0);
         }
      } else {
         return super.__cmp__(other);
      }
   }

   public boolean __nonzero__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__nonzero__");
      if (impl == null) {
         impl = self_type.lookup("__len__");
         if (impl == null) {
            return super.__nonzero__();
         }
      }

      PyObject o = impl.__get__(this, self_type).__call__();
      Class c = o.getClass();
      if (c != PyInteger.class && c != PyBoolean.class) {
         throw Py.TypeError(String.format("__nonzero__ should return bool or int, returned %s", self_type.getName()));
      } else {
         return o.__nonzero__();
      }
   }

   public boolean __contains__(PyObject o) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__contains__");
      return impl == null ? super.__contains__(o) : impl.__get__(this, self_type).__call__(o).__nonzero__();
   }

   public int __len__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__len__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         return res.asInt();
      } else {
         return super.__len__();
      }
   }

   public PyObject __iter__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__iter__");
      if (impl != null) {
         return impl.__get__(this, self_type).__call__();
      } else {
         impl = self_type.lookup("__getitem__");
         return (PyObject)(impl == null ? super.__iter__() : new PySequenceIter(this));
      }
   }

   public PyObject __iternext__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("next");
      if (impl != null) {
         try {
            return impl.__get__(this, self_type).__call__();
         } catch (PyException var4) {
            if (var4.match(Py.StopIteration)) {
               return null;
            } else {
               throw var4;
            }
         }
      } else {
         return super.__iternext__();
      }
   }

   public PyObject __finditem__(PyObject key) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__getitem__");
      if (impl != null) {
         try {
            return impl.__get__(this, self_type).__call__(key);
         } catch (PyException var5) {
            if (var5.match(Py.LookupError)) {
               return null;
            } else {
               throw var5;
            }
         }
      } else {
         return super.__finditem__(key);
      }
   }

   public PyObject __finditem__(int key) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__getitem__");
      if (impl != null) {
         try {
            return impl.__get__(this, self_type).__call__((PyObject)(new PyInteger(key)));
         } catch (PyException var5) {
            if (var5.match(Py.LookupError)) {
               return null;
            } else {
               throw var5;
            }
         }
      } else {
         return super.__finditem__(key);
      }
   }

   public PyObject __getitem__(PyObject key) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__getitem__");
      return impl != null ? impl.__get__(this, self_type).__call__(key) : super.__getitem__(key);
   }

   public void __setitem__(PyObject key, PyObject value) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__setitem__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__(key, value);
      } else {
         super.__setitem__(key, value);
      }
   }

   public PyObject __getslice__(PyObject start, PyObject stop, PyObject step) {
      if (step != null) {
         return this.__getitem__(new PySlice(start, stop, step));
      } else {
         PyType self_type = this.getType();
         PyObject impl = self_type.lookup("__getslice__");
         if (impl != null) {
            PyObject[] indices = PySlice.indices2(this, start, stop);
            return impl.__get__(this, self_type).__call__(indices[0], indices[1]);
         } else {
            return super.__getslice__(start, stop, step);
         }
      }
   }

   public void __setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      if (step != null) {
         this.__setitem__(new PySlice(start, stop, step), value);
      } else {
         PyType self_type = this.getType();
         PyObject impl = self_type.lookup("__setslice__");
         if (impl != null) {
            PyObject[] indices = PySlice.indices2(this, start, stop);
            impl.__get__(this, self_type).__call__(indices[0], indices[1], value);
         } else {
            super.__setslice__(start, stop, step, value);
         }
      }
   }

   public void __delslice__(PyObject start, PyObject stop, PyObject step) {
      if (step != null) {
         this.__delitem__(new PySlice(start, stop, step));
      } else {
         PyType self_type = this.getType();
         PyObject impl = self_type.lookup("__delslice__");
         if (impl != null) {
            PyObject[] indices = PySlice.indices2(this, start, stop);
            impl.__get__(this, self_type).__call__(indices[0], indices[1]);
         } else {
            super.__delslice__(start, stop, step);
         }
      }
   }

   public void __delitem__(PyObject key) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__delitem__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__(key);
      } else {
         super.__delitem__(key);
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__call__");
      return impl != null ? impl.__get__(this, self_type).__call__(args, keywords) : super.__call__(args, keywords);
   }

   public PyObject __findattr_ex__(String name) {
      return Deriveds.__findattr_ex__(this, name);
   }

   public void __setattr__(String name, PyObject value) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__setattr__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__((PyObject)PyString.fromInterned(name), (PyObject)value);
      } else {
         super.__setattr__(name, value);
      }
   }

   public void __delattr__(String name) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__delattr__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__((PyObject)PyString.fromInterned(name));
      } else {
         super.__delattr__(name);
      }
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__get__");
      if (impl != null) {
         if (obj == null) {
            obj = Py.None;
         }

         if (type == null) {
            type = Py.None;
         }

         return impl.__get__(this, self_type).__call__(obj, type);
      } else {
         return super.__get__(obj, type);
      }
   }

   public void __set__(PyObject obj, PyObject value) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__set__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__(obj, value);
      } else {
         super.__set__(obj, value);
      }
   }

   public void __delete__(PyObject obj) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__delete__");
      if (impl != null) {
         impl.__get__(this, self_type).__call__(obj);
      } else {
         super.__delete__(obj);
      }
   }

   public PyObject __pow__(PyObject other, PyObject modulo) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__pow__");
      if (impl != null) {
         PyObject res;
         if (modulo == null) {
            res = impl.__get__(this, self_type).__call__(other);
         } else {
            res = impl.__get__(this, self_type).__call__(other, modulo);
         }

         return res == Py.NotImplemented ? null : res;
      } else {
         return super.__pow__(other, modulo);
      }
   }

   public void dispatch__init__(PyObject[] args, String[] keywords) {
      Deriveds.dispatch__init__(this, args, keywords);
   }

   public PyObject __index__() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__index__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (!(res instanceof PyInteger) && !(res instanceof PyLong)) {
            throw Py.TypeError(String.format("__index__ returned non-(int,long) (type %s)", res.getType().fastGetName()));
         } else {
            return res;
         }
      } else {
         return super.__index__();
      }
   }

   public Object __tojava__(Class c) {
      if (c != Object.class && c != Serializable.class && c.isInstance(this)) {
         return this;
      } else {
         PyType self_type = this.getType();
         PyObject impl = self_type.lookup("__tojava__");
         if (impl != null) {
            PyObject delegate = impl.__get__(this, self_type).__call__(Py.java2py(c));
            if (delegate != this) {
               return delegate.__tojava__(Object.class);
            }
         }

         return super.__tojava__(c);
      }
   }

   public Object __coerce_ex__(PyObject o) {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__coerce__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__(o);
         if (res == Py.NotImplemented) {
            return Py.None;
         } else if (!(res instanceof PyTuple)) {
            throw Py.TypeError("__coerce__ didn't return a 2-tuple");
         } else {
            return ((PyTuple)res).getArray();
         }
      } else {
         return super.__coerce_ex__(o);
      }
   }

   public String toString() {
      PyType self_type = this.getType();
      PyObject impl = self_type.lookup("__repr__");
      if (impl != null) {
         PyObject res = impl.__get__(this, self_type).__call__();
         if (!(res instanceof PyString)) {
            throw Py.TypeError("__repr__ returned non-string (type " + res.getType().fastGetName() + ")");
         } else {
            return ((PyString)res).toString();
         }
      } else {
         return super.toString();
      }
   }
}
