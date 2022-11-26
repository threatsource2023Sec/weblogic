package org.apache.velocity.test;

import java.io.StringWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;

public class ParserTestCase extends TestCase {
   // $FF: synthetic field
   static Class class$org$apache$velocity$test$ParserTestCase;

   public ParserTestCase(String testName) {
      super(testName);
   }

   public static Test suite() {
      return new TestSuite(class$org$apache$velocity$test$ParserTestCase == null ? (class$org$apache$velocity$test$ParserTestCase = class$("org.apache.velocity.test.ParserTestCase")) : class$org$apache$velocity$test$ParserTestCase);
   }

   public void testEquals() throws Exception {
      VelocityEngine ve = new VelocityEngine();
      ve.init();
      String template = "#if($a == $b) foo #end";
      ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
      template = "#if($a = $b) foo #end";

      try {
         ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
         Assert.assertTrue(false);
      } catch (ParseErrorException var4) {
      }

   }

   public void testMacro() throws Exception {
      VelocityEngine ve = new VelocityEngine();
      ve.init();
      String template = "#macro(foo) foo #end";
      ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
      template = "#macro($x) foo #end";

      try {
         ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
         Assert.assertTrue(false);
      } catch (ParseErrorException var4) {
      }

   }

   public void testArgs() throws Exception {
      VelocityEngine ve = new VelocityEngine();
      ve.init();
      String template = "#macro(foo) foo #end";
      ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
      template = "#foreach(  $i     in  $woogie   ) end #end";
      ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
      template = "#macro(   foo $a) $a #end #foo(woogie)";

      try {
         ve.evaluate(new VelocityContext(), new StringWriter(), "foo", (String)template);
         Assert.assertTrue(false);
      } catch (ParseErrorException var4) {
         System.out.println("Caught pee!");
      }

   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
