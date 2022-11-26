package org.python.core;

public class NewCompilerResources {
   public static void importAll(PyObject module, PyFrame frame) {
      boolean filter = true;
      PyObject names;
      if (module instanceof PyJavaPackage) {
         names = ((PyJavaPackage)module).fillDir();
      } else {
         PyObject __all__ = module.__findattr__("__all__");
         if (__all__ != null) {
            names = __all__;
            filter = false;
         } else {
            names = module.__dir__();
         }
      }

      loadNames(names, module, frame.getLocals(), filter);
   }

   private static void loadNames(PyObject names, PyObject module, PyObject locals, boolean filter) {
      PyObject iter = names.__iter__();

      while(true) {
         String sname;
         do {
            PyObject name;
            if ((name = iter.__iternext__()) == null) {
               return;
            }

            sname = ((PyString)name).internedString();
         } while(filter && sname.startsWith("_"));

         try {
            locals.__setitem__(sname, module.__getattr__(sname));
         } catch (Exception var8) {
         }
      }
   }
}
