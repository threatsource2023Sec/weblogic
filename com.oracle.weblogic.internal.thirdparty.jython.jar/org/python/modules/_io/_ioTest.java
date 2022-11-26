package org.python.modules._io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.core.io.RawIOBase;
import org.python.util.PythonInterpreter;

public class _ioTest {
   private final String FILE1 = "$test_1_tmp";
   private final String FILE2 = "$test_2_tmp";
   private final String FILE3 = "$test_3_tmp";
   PySystemState systemState;
   PyStringMap dict;
   PythonInterpreter interp;

   @Before
   public void setUp() throws Exception {
      this.systemState = Py.getSystemState();
      this.dict = new PyStringMap();
      this.interp = new PythonInterpreter(this.dict, this.systemState);
   }

   @Test
   public void moduleImport() {
      this.interp.exec("import _io");
      PyObject _io = this.interp.get("_io");
      Assert.assertNotNull(_io);
   }

   @Test
   public void javaRaiseUnsupportedOperation() {
      this.interp.exec("import io");
      PyException pye = _jyio.UnsupportedOperation("Message from _ioTest");
      PyObject type = pye.type;
      String repr = type.toString();
      Assert.assertEquals("Class name", "<class '_io.UnsupportedOperation'>", repr);
      this.interp.exec("try :\n    io.IOBase().fileno()\nexcept Exception as e:\n    pass");
      PyObject e = this.interp.get("e");
      String m = e.toString();
      Assert.assertThat(m, JUnitMatchers.both(JUnitMatchers.containsString("UnsupportedOperation")).and(JUnitMatchers.containsString("fileno")));
   }

   @Test
   public void pythonRaiseUnsupportedOperation() {
      this.interp.exec("import _io");

      try {
         this.interp.exec("raise _io.UnsupportedOperation()");
         Assert.fail("_io.UnsupportedOperation not raised when expected");
      } catch (PyException var2) {
         Assert.assertEquals(_jyio.UnsupportedOperation, var2.type);
      }

   }

   @Test
   public void openPyFileByFileno() throws IOException {
      PySystemState sys = Py.getSystemState();
      PyFile file = new PyFile("$test_1_tmp", "w", 1);
      this.openByFilenoTest(file, "wb");
   }

   @Test
   public void openPyFileOStreamByFileno() throws IOException {
      PySystemState sys = Py.getSystemState();
      OutputStream ostream = new FileOutputStream("$test_1_tmp");
      PyFile file = new PyFile(ostream);
      this.openByFilenoTest(file, "wb");
   }

   @Test
   public void openStdinByFileno() throws IOException {
      PySystemState sys = Py.getSystemState();
      this.openByFilenoTest(sys.stdin, "rb");
   }

   @Test
   public void openStdoutByFileno() throws IOException {
      PySystemState sys = Py.getSystemState();
      this.openByFilenoTest(sys.stdout, "wb");
   }

   @Test
   public void openStderrByFileno() throws IOException {
      PySystemState sys = Py.getSystemState();
      this.openByFilenoTest(sys.stderr, "wb");
   }

   public void openByFilenoTest(PyObject file, String mode) throws IOException {
      PyObject pyfd = file.invoke("fileno");
      RawIOBase fd = (RawIOBase)pyfd.__tojava__(RawIOBase.class);
      PyObject[] args = new PyObject[]{pyfd, Py.newString(mode), Py.False};
      String[] kwds = new String[]{"closefd"};
      PyObject file2 = _jyio.open(args, kwds);
      file2.invoke("close");
   }

   @Test
   public void closeNeglectedFiles() throws IOException {
      String F = "$test_1_tmp";
      String FB = "$test_2_tmp";
      String FT = "$test_3_tmp";
      String expText = "Line 1\nLine 2\nLine 3.";
      byte[] expBytes = expText.getBytes();
      String escapedText = expText.replace("\n", "\\n");
      this.interp.exec("import io\nu = u'" + escapedText + "'\n" + "b = b'" + escapedText + "'\n");
      this.interp.exec("f = io.open('$test_1_tmp', 'wb', 0)");
      PyIOBase pyf = (PyIOBase)this.interp.get("f");
      Assert.assertNotNull(pyf);
      this.interp.exec("fb = io.open('$test_2_tmp', 'wb')");
      PyIOBase pyfb = (PyIOBase)this.interp.get("fb");
      Assert.assertNotNull(pyfb);
      this.interp.exec("ft = io.open('$test_3_tmp', 'w', encoding='ascii')");
      PyIOBase pyft = (PyIOBase)this.interp.get("ft");
      Assert.assertNotNull(pyft);
      this.interp.exec("f.write(b)");
      this.interp.exec("fb.write(b)");
      this.interp.exec("ft.write(u)");
      this.interp.cleanup();
      Assert.assertTrue(pyf.__closed);
      Assert.assertTrue(pyfb.__closed);
      Assert.assertTrue(pyft.__closed);
      checkFileContent("$test_1_tmp", expBytes, true);
      checkFileContent("$test_2_tmp", expBytes, true);
      checkFileContent("$test_3_tmp", newlineFix(expText), true);
   }

   @Test
   public void closeNeglectedPyFiles() throws IOException {
      String F = "$test_1_tmp";
      String FB = "$test_2_tmp";
      String FT = "$test_3_tmp";
      String expText = "Line 1\nLine 2\nLine 3.";
      byte[] expBytes = expText.getBytes();
      String escapedText = expText.replace("\n", "\\n");
      this.interp.exec("import io\nu = u'" + escapedText + "'\n" + "b = b'" + escapedText + "'\n");
      this.interp.exec("f = open('$test_1_tmp', 'wb', 0)");
      PyFile pyf = (PyFile)this.interp.get("f");
      Assert.assertNotNull(pyf);
      RawIOBase r = (RawIOBase)pyf.fileno().__tojava__(RawIOBase.class);
      this.interp.exec("fb = open('$test_2_tmp', 'wb')");
      PyFile pyfb = (PyFile)this.interp.get("fb");
      Assert.assertNotNull(pyfb);
      RawIOBase rb = (RawIOBase)pyfb.fileno().__tojava__(RawIOBase.class);
      this.interp.exec("ft = open('$test_3_tmp', 'w')");
      PyFile pyft = (PyFile)this.interp.get("ft");
      Assert.assertNotNull(pyft);
      RawIOBase rt = (RawIOBase)pyft.fileno().__tojava__(RawIOBase.class);
      this.interp.exec("f.write(b)");
      this.interp.exec("fb.write(b)");
      this.interp.exec("ft.write(u)");
      this.interp.cleanup();
      Assert.assertTrue(r.closed());
      Assert.assertTrue(rb.closed());
      Assert.assertTrue(rt.closed());
      checkFileContent("$test_1_tmp", expBytes, true);
      checkFileContent("$test_2_tmp", expBytes, true);
      checkFileContent("$test_3_tmp", newlineFix(expText), true);
   }

   private static void checkFileContent(String name, byte[] expBytes, boolean delete) throws IOException {
      byte[] r = new byte[2 * expBytes.length];
      File f = new File(name);
      FileInputStream in = new FileInputStream(f);
      int n = in.read(r);
      in.close();
      String msg = "Bytes read from " + name;
      Assert.assertEquals(msg, (long)expBytes.length, (long)n);
      byte[] resBytes = Arrays.copyOf(r, n);
      Assert.assertArrayEquals(msg, expBytes, resBytes);
      if (delete) {
         f.delete();
      }

   }

   private static byte[] newlineFix(String expText) {
      String newline = System.getProperty("line.separator");
      return expText.replace("\n", newline).getBytes();
   }
}
