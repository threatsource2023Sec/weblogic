package org.python.core;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import jnr.posix.util.Platform;
import junit.framework.TestCase;
import org.python.util.PythonInterpreter;

public class PySystemStateTest extends TestCase {
   public void testGetJarFileNameFromURL() throws Exception {
      assertNull(Py.getJarFileNameFromURL((URL)null));
      String urlString = "jar:file:/some_dir/some.jar!/a/package/with/A.class";
      URL url = new URL(urlString);
      assertEquals("/some_dir/some.jar", Py.getJarFileNameFromURL(url));
      urlString = "jar:file:/some%20dir/some.jar!/a/package/with/A.class";
      url = new URL(urlString);
      assertEquals("/some dir/some.jar", Py.getJarFileNameFromURL(url));
      urlString = "jar:file:/some+dir/some.jar!/a/package/with/A.class";
      url = new URL(urlString);
      assertEquals("/some+dir/some.jar", Py.getJarFileNameFromURL(url));
   }

   public void testGetJarFileNameFromURL_jboss() throws Exception {
      String protocol = "vfszip";
      String host = "";
      int port = true;
      URLStreamHandler handler = new TestJBossURLStreamHandler();
      String file;
      URL url;
      if (Platform.IS_WINDOWS) {
         file = "/C:/some_dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/C:/some_dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("C:/some_dir/some.jar", Py.getJarFileNameFromURL(url));
         file = "/C:/some%20dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/C:/some%20dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("C:/some dir/some.jar", Py.getJarFileNameFromURL(url));
         file = "/C:/some+dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/C:/some+dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("C:/some+dir/some.jar", Py.getJarFileNameFromURL(url));
      } else {
         file = "/some_dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/some_dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("/some_dir/some.jar", Py.getJarFileNameFromURL(url));
         file = "/some%20dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/some%20dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("/some dir/some.jar", Py.getJarFileNameFromURL(url));
         file = "/some+dir/some.jar/org/python/core/PySystemState.class";
         url = new URL("vfszip", "", -1, file, handler);
         assertEquals("vfszip:/some+dir/some.jar/org/python/core/PySystemState.class", url.toString());
         assertEquals("/some+dir/some.jar", Py.getJarFileNameFromURL(url));
      }

   }

   public void testImport() throws Exception {
      Options.importSite = false;

      try {
         PySystemState pySystemState = new PySystemState();
         PySystemState.initialize();
         PythonInterpreter interpreter = new PythonInterpreter((PyObject)null, pySystemState);
         interpreter.exec("import os");
         assertTrue(interpreter.getSystemState().modules.__contains__(new PyString("os")));
      } finally {
         Options.importSite = true;
      }

   }

   protected static class TestJBossURLStreamHandler extends URLStreamHandler {
      protected URLConnection openConnection(URL u) throws IOException {
         throw new RuntimeException("unexpected call to openConnection " + u.toString());
      }
   }
}
