package org.python.expose;

import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyMethodDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.core.PyType;

public class BaseTypeBuilder implements TypeBuilder {
   private PyNewWrapper newWrapper;
   private PyBuiltinMethod[] meths;
   private PyDataDescr[] descrs;
   private Class typeClass;
   private Class baseClass;
   private String name;
   private boolean isBaseType;
   private String doc;

   public BaseTypeBuilder(String name, Class typeClass, Class baseClass, boolean isBaseType, String doc, PyBuiltinMethod[] meths, PyDataDescr[] descrs, PyNewWrapper newWrapper) {
      this.typeClass = typeClass;
      this.baseClass = baseClass;
      this.isBaseType = isBaseType;
      this.doc = doc;
      this.name = name;
      this.descrs = descrs;
      this.meths = meths;
      this.newWrapper = newWrapper;
   }

   public PyObject getDict(PyType type) {
      PyStringMap dict = new PyStringMap();
      PyBuiltinMethod[] var3 = this.meths;
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         PyBuiltinMethod func = var3[var5];
         PyMethodDescr pmd = func.makeDescriptor(type);
         dict.__setitem__((String)pmd.getName(), pmd);
      }

      PyDataDescr[] var8 = this.descrs;
      var4 = var8.length;

      for(var5 = 0; var5 < var4; ++var5) {
         PyDataDescr descr = var8[var5];
         descr.setType(type);
         dict.__setitem__((String)descr.getName(), descr);
      }

      if (this.newWrapper != null) {
         dict.__setitem__((String)"__new__", this.newWrapper);
         this.newWrapper.setWrappedType(type);
      }

      return dict;
   }

   public String getName() {
      return this.name;
   }

   public Class getTypeClass() {
      return this.typeClass;
   }

   public Class getBase() {
      return this.baseClass;
   }

   public boolean getIsBaseType() {
      return this.isBaseType;
   }

   public String getDoc() {
      return this.doc;
   }
}
