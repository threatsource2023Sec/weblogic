package org.python.compiler;

import java.lang.reflect.Array;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.python.core.ClassDictInit;
import org.python.core.PyObject;
import org.python.core.PyProxy;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class JavaMakerSmokeTest {
   public PythonInterpreter interp;
   public Class proxyClass;

   @Before
   public void setUp() throws Exception {
      Properties props = new Properties(System.getProperties());
      props.setProperty("python.cachedir.skip", "true");
      PySystemState.initialize(props, (Properties)null);
      this.interp = new PythonInterpreter();
      String input = new String();
      input = input + "import java.io.ByteArrayInputStream\n";
      input = input + "import java.lang.String\n";
      input = input + "import org.python.core.Options\n";
      input = input + "org.python.core.Options.proxyDebugDirectory = 'build/classes'\n";
      input = input + "class ProxyTest(java.io.ByteArrayInputStream):\n";
      input = input + "    def somemethod(self): pass\n";
      input = input + "ProxyTest(java.lang.String('teststr').getBytes())\n";
      this.interp.exec(input);
      this.proxyClass = Class.forName("org.python.proxies.__main__$ProxyTest$0");
   }

   @Test
   public void constructors() throws Exception {
      this.proxyClass.getConstructor(Array.newInstance(Byte.TYPE, 0).getClass());
      this.proxyClass.getConstructor(Array.newInstance(Byte.TYPE, 0).getClass(), Integer.TYPE, Integer.TYPE);
   }

   @Test
   public void methods() throws Exception {
      this.proxyClass.getMethod("classDictInit", PyObject.class);
      this.proxyClass.getMethod("close");
   }

   @Test
   public void annotations() throws Exception {
      this.proxyClass.getAnnotation(APIVersion.class);
      this.proxyClass.getAnnotation(MTime.class);
   }

   @Test
   public void interfaces() throws Exception {
      Class[] interfaces = new Class[]{PyProxy.class, ClassDictInit.class};
      Assert.assertArrayEquals(interfaces, this.proxyClass.getInterfaces());
   }
}
