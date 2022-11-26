package org.apache.velocity.test;

import junit.framework.Assert;
import junit.framework.Test;
import org.apache.velocity.util.StringUtils;

public class MiscTestCase extends BaseTestCase {
   public MiscTestCase() {
      super("MiscTestCase");
   }

   public MiscTestCase(String name) {
      super(name);
   }

   public static Test suite() {
      return new MiscTestCase();
   }

   public void runTest() {
      String eol = "XY";
      String arg = "XY";
      String res = StringUtils.chop(arg, 1, eol);
      Assert.assertTrue("Test 1", res.equals(""));
      arg = "X";
      res = StringUtils.chop(arg, 1, eol);
      Assert.assertTrue("Test 2", res.equals(""));
      arg = "ZXY";
      res = StringUtils.chop(arg, 1, eol);
      Assert.assertTrue("Test 3", res.equals("Z"));
      arg = "Hello!";
      res = StringUtils.chop(arg, 2, eol);
      Assert.assertTrue("Test 4", res.equals("Hell"));
   }
}
