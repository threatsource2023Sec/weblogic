package org.python.compiler.custom_proxymaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.python.util.ProxyCompiler;

public class ClassAnnotationTest {
   Class proxy;

   @Before
   public void setUp() throws Exception {
      ProxyCompiler.compile("tests/python/custom_proxymaker/annotated_class.py", "build/classes");
      this.proxy = Class.forName("custom_proxymaker.tests.AnnotatedInputStream");
   }

   @Test
   public void hasClassAnnotation() {
      Deprecated deprecatedAnnotation = (Deprecated)this.proxy.getAnnotation(Deprecated.class);
   }

   @Test
   public void hasCustomAnnotationWithFields() throws Exception {
      CustomAnnotation customAnnotation = (CustomAnnotation)this.proxy.getAnnotation(CustomAnnotation.class);
      Assert.assertEquals("Darusik", customAnnotation.createdBy());
      Assert.assertEquals(CustomAnnotation.Priority.LOW, customAnnotation.priority());
      Assert.assertArrayEquals(new String[]{"Darjus", "Darjunia"}, customAnnotation.changedBy());
   }
}
