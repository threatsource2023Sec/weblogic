package org.python.compiler.custom_proxymaker;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.python.util.ProxyCompiler;

public class JUnitTest {
   @Test
   public void testMethodSignatures() throws Exception {
      ProxyCompiler.compile("tests/python/custom_proxymaker/junit_test.py", "build/classes");
      JUnitCore.runClasses(new Class[]{Class.forName("custom_proxymaker.tests.JUnitTest")});
   }
}
