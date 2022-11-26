package org.python.core;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.python.compiler.AdapterMaker;
import org.python.compiler.CustomMaker;
import org.python.compiler.JavaMaker;

class MakeProxies {
   private static final String proxyPrefix = "org.python.proxies.";
   private static int proxyNumber = 0;

   private static Class makeClass(Class referent, List secondary, String name, ByteArrayOutputStream bytes) {
      List referents = null;
      if (secondary != null) {
         if (referent != null) {
            secondary.add(0, referent);
         }

         referents = secondary;
      } else if (referent != null) {
         referents = new ArrayList(1);
         ((List)referents).add(referent);
      }

      return BytecodeLoader.makeClass(name, (List)referents, (byte[])bytes.toByteArray());
   }

   public static Class makeAdapter(Class c) {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      AdapterMaker maker = new AdapterMaker("org.python.proxies." + c.getName(), c);

      try {
         maker.build(bytes);
      } catch (Exception var4) {
         throw Py.JavaError(var4);
      }

      Py.saveClassFile(maker.myClass, bytes);
      return makeClass(c, (List)null, maker.myClass, bytes);
   }

   public static synchronized Class makeProxy(Class superclass, List vinterfaces, String className, String proxyName, PyObject dict) {
      JavaMaker javaMaker = null;
      Class[] interfaces = (Class[])vinterfaces.toArray(new Class[vinterfaces.size()]);
      String fullProxyName = "org.python.proxies." + proxyName + "$" + proxyNumber++;
      PyObject module = dict.__finditem__("__module__");
      String pythonModuleName;
      if (module == null) {
         pythonModuleName = "foo";
      } else {
         pythonModuleName = (String)module.__tojava__(String.class);
      }

      PyObject userDefinedProxyMaker = dict.__finditem__("__proxymaker__");
      if (userDefinedProxyMaker != null) {
         if (module == null) {
            throw Py.TypeError("Classes using __proxymaker__ must define __module__");
         }

         PyObject[] args = Py.javas2pys(superclass, interfaces, className, pythonModuleName, fullProxyName, dict);
         javaMaker = (JavaMaker)Py.tojava(userDefinedProxyMaker.__call__(args), JavaMaker.class);
         if (javaMaker instanceof CustomMaker) {
            CustomMaker customMaker = (CustomMaker)javaMaker;
            return customMaker.makeClass();
         }
      }

      if (javaMaker == null) {
         javaMaker = new JavaMaker(superclass, interfaces, className, pythonModuleName, fullProxyName, dict);
      }

      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         javaMaker.build(bytes);
         Py.saveClassFile(javaMaker.myClass, bytes);
         return makeClass(superclass, vinterfaces, javaMaker.myClass, bytes);
      } catch (Exception var13) {
         throw Py.JavaError(var13);
      }
   }
}
