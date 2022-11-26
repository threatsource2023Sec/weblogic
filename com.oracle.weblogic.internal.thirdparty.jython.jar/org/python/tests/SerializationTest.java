package org.python.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import junit.framework.TestCase;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyFrozenSet;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PySet;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.core.PyTuple;
import org.python.util.PythonInterpreter;
import org.python.util.PythonObjectInputStream;

public class SerializationTest extends TestCase {
   private PythonInterpreter interp;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
      this.interp.exec("from java.io import Serializable");
      this.interp.exec("class Test(Serializable): pass");
      this.interp.exec("x = Test()");
   }

   public void testDirect() throws IOException, ClassNotFoundException {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      (new ObjectOutputStream(os)).writeObject(this.interp.get("x"));
      (new PythonObjectInputStream(new ByteArrayInputStream(os.toByteArray()))).readObject();
   }

   public void testJython() {
      this.interp.set("t", (Object)this);
      this.interp.exec("t.testDirect()");
   }

   public void testBasicTypes() {
      this.assertRoundtrip(Py.None);
      this.assertRoundtrip(Py.True);
      this.assertRoundtrip(Py.False);
      this.assertRoundtrip(Py.newInteger(42));
      this.assertRoundtrip(Py.newLong(47));
      this.assertRoundtrip(Py.newString("Jython: Python for the Java Platform"));
      this.assertRoundtrip(Py.newUnicode("Drink options include \ud83c\udf7a, \ud83c\udf75, \ud83c\udf77, and ☕"));
      Map map = new HashMap();
      map.put(Py.newString("OEIS interesting number"), Py.newInteger(14228));
      map.put(Py.newString("Hardy-Ramanujan number"), Py.newInteger(1729));
      this.assertRoundtrip(new PyDictionary(map));
      this.assertRoundtrip(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(28), Py.newInteger(546), Py.newInteger(9450), Py.newInteger(157773)}));
      this.assertRoundtrip(new PySet(new PyObject[]{Py.Zero, Py.One}));
      this.assertRoundtrip(new PyFrozenSet(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})));
      this.assertRoundtrip(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(8), Py.newInteger(248), Py.newInteger(113281)}));
   }

   public void assertRoundtrip(Object obj) {
      try {
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         CloneOutput serializer = new CloneOutput(output);
         serializer.writeObject(obj);
         serializer.close();
         ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
         CloneInput unserializer = new CloneInput(input, serializer);
         assertEquals(obj, unserializer.readObject());
      } catch (IOException var6) {
         throw new AssertionError(var6);
      } catch (ClassNotFoundException var7) {
         throw new AssertionError(var7);
      }
   }

   private static class CloneInput extends ObjectInputStream {
      private final CloneOutput output;

      CloneInput(InputStream in, CloneOutput output) throws IOException {
         super(in);
         this.output = output;
      }

      protected Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
         Class c = (Class)this.output.classQueue.poll();
         String expected = osc.getName();
         String found = c == null ? null : c.getName();
         if (!expected.equals(found)) {
            throw new InvalidClassException("Classes desynchronized: found " + found + " when expecting " + expected);
         } else {
            return c;
         }
      }

      protected Class resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
         return (Class)this.output.classQueue.poll();
      }
   }

   private static class CloneOutput extends ObjectOutputStream {
      Queue classQueue = new LinkedList();

      CloneOutput(OutputStream out) throws IOException {
         super(out);
      }

      protected void annotateClass(Class c) {
         this.classQueue.add(c);
      }

      protected void annotateProxyClass(Class c) {
         this.classQueue.add(c);
      }
   }
}
