package org.python.core;

import java.math.BigInteger;
import junit.framework.TestCase;
import org.python.core.stringlib.FieldNameIterator;
import org.python.core.stringlib.IntegerFormatter;
import org.python.core.stringlib.InternalFormat;
import org.python.core.stringlib.MarkupIterator;
import org.python.core.stringlib.TextFormatter;
import org.python.util.PythonInterpreter;

public class StringFormatTest extends TestCase {
   PythonInterpreter interp = new PythonInterpreter();
   private boolean useBytes = true;

   public void testInternalFormatSpec() {
      InternalFormat.Spec spec = InternalFormat.fromText("x");
      assertFalse(InternalFormat.Spec.specified(spec.align));
      assertFalse(InternalFormat.Spec.specified(spec.fill));
      assertFalse(InternalFormat.Spec.specified(spec.width));
      assertFalse(InternalFormat.Spec.specified(spec.precision));
      assertEquals('x', spec.type);
      spec = InternalFormat.fromText("<x");
      assertEquals('<', spec.align);
      assertEquals('x', spec.type);
      spec = InternalFormat.fromText("~<x");
      assertEquals('~', spec.fill);
      assertEquals('<', spec.align);
      assertEquals('x', spec.type);
      spec = InternalFormat.fromText("+x");
      assertEquals('+', spec.sign);
      assertEquals('x', spec.type);
      spec = InternalFormat.fromText("#x");
      assertEquals(true, spec.alternate);
      spec = InternalFormat.fromText("0x");
      assertEquals('=', spec.align);
      assertEquals('0', spec.fill);
      spec = InternalFormat.fromText("123x");
      assertEquals(123, spec.width);
      spec = InternalFormat.fromText("123.456x");
      assertEquals(123, spec.width);
      assertEquals(456, spec.precision);
      this.assertParseError("123.x", "Format specifier missing precision");
      this.assertParseError("123xx", "Invalid conversion specification");
      spec = InternalFormat.fromText("");
      assertEquals('\uffff', spec.type);
   }

   private void assertParseError(String spec, String expected) {
      String error = null;

      try {
         InternalFormat.fromText(spec);
      } catch (PyException var5) {
         assertEquals(Py.ValueError, var5.type);
         error = var5.value.toString();
      }

      assertEquals(expected, error);
   }

   public void testPrepareFormatter() {
      int v = 123;
      IntegerFormatter f = PyInteger.prepareFormatter(InternalFormat.fromText("d"));
      assertEquals("123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("o"));
      assertEquals("173", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("x"));
      assertEquals("7b", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("X"));
      assertEquals("7B", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("b"));
      assertEquals("1111011", f.format(v).pad().getResult());
      int v2 = 1234567890;
      f = PyInteger.prepareFormatter(InternalFormat.fromText(",d"));
      assertEquals("1,234,567,890", f.format(v2).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("#o"));
      assertEquals("0o173", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("#X"));
      assertEquals("0X7B", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      assertEquals("{", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("+d"));
      assertEquals("+123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText(" d"));
      assertEquals(" 123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("5"));
      assertEquals("  123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("^6"));
      assertEquals(" 123  ", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("~<5"));
      assertEquals("123~~", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("0=+6"));
      assertEquals("+00123", f.format(v).pad().getResult());
      this.assertValueError("0=+6.1", "Precision not allowed in integer format specifier");
      this.assertValueError("+c", "Sign not allowed with integer format specifier 'c'");
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      f.setBytes(true);
      this.assertOverflowError(256, f, "%c arg not in range(0x100)");
      this.assertOverflowError(-1, f, "%c arg not in range(0x100)");
      this.assertOverflowError(1114112, f, "%c arg not in range(0x100)");
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      this.assertOverflowError(1114112, f, "%c arg not in range(0x110000)");
      this.assertOverflowError(-1, f, "%c arg not in range(0x110000)");
   }

   public void testPrepareFormatterLong() {
      BigInteger v = BigInteger.valueOf(123L);
      IntegerFormatter f = PyInteger.prepareFormatter(InternalFormat.fromText("d"));
      assertEquals("123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("o"));
      assertEquals("173", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("x"));
      assertEquals("7b", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("X"));
      assertEquals("7B", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("b"));
      assertEquals("1111011", f.format(v).pad().getResult());
      BigInteger v2 = BigInteger.valueOf(1234567890L);
      f = PyInteger.prepareFormatter(InternalFormat.fromText(",d"));
      assertEquals("1,234,567,890", f.format(v2).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("#o"));
      assertEquals("0o173", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("#X"));
      assertEquals("0X7B", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      assertEquals("{", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("+d"));
      assertEquals("+123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText(" d"));
      assertEquals(" 123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("5"));
      assertEquals("  123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("^6"));
      assertEquals(" 123  ", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("~<5"));
      assertEquals("123~~", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("0=+6"));
      assertEquals("+00123", f.format(v).pad().getResult());
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      f.setBytes(true);
      this.assertOverflowError(BigInteger.valueOf(256L), f, "%c arg not in range(0x100)");
      this.assertOverflowError(BigInteger.valueOf(-1L), f, "%c arg not in range(0x100)");
      this.assertOverflowError(BigInteger.valueOf(1114112L), f, "%c arg not in range(0x100)");
      f = PyInteger.prepareFormatter(InternalFormat.fromText("c"));
      this.assertOverflowError(BigInteger.valueOf(1114112L), f, "%c arg not in range(0x110000)");
      this.assertOverflowError(BigInteger.valueOf(-1L), f, "%c arg not in range(0x110000)");
   }

   private void assertValueError(String formatSpec, String expected) {
      try {
         IntegerFormatter f = PyInteger.prepareFormatter(InternalFormat.fromText(formatSpec));
         fail("ValueError not thrown, expected: " + expected);
      } catch (PyException var4) {
         assertEquals(expected, var4.value.toString());
      }

   }

   private void assertOverflowError(int v, IntegerFormatter f, String expected) {
      try {
         f.format(v).pad().getResult();
         fail("OverflowError not thrown, expected: " + expected);
      } catch (PyException var5) {
         assertEquals(expected, var5.value.toString());
      }

   }

   private void assertOverflowError(BigInteger v, IntegerFormatter f, String expected) {
      try {
         f.format(v).pad().getResult();
         fail("OverflowError not thrown, expected: " + expected);
      } catch (PyException var5) {
         assertEquals(expected, var5.value.toString());
      }

   }

   public void testFormatString() {
      String v = "abc";
      TextFormatter f = PyString.prepareFormatter(InternalFormat.fromText(""));
      assertEquals("abc", f.format(v).pad().getResult());
      String v2 = "abcdef";
      f = PyString.prepareFormatter(InternalFormat.fromText(".3"));
      assertEquals("abc", f.format(v2).pad().getResult());
      f = PyString.prepareFormatter(InternalFormat.fromText("6"));
      assertEquals("abc   ", f.format(v).pad().getResult());
   }

   public void implTestMarkupIterator() {
      MarkupIterator iterator = this.newMarkupIterator("abc");
      assertEquals("abc", iterator.nextChunk().literalText);
      assertNull(iterator.nextChunk());
      iterator = this.newMarkupIterator("First, thou shalt count to {0}");
      MarkupIterator.Chunk chunk = iterator.nextChunk();
      assertEquals("First, thou shalt count to ", chunk.literalText);
      assertEquals("0", chunk.fieldName);
      assertNull(iterator.nextChunk());
      iterator = this.newMarkupIterator("Weight in tons {0.weight!r:s}");
      chunk = iterator.nextChunk();
      assertEquals("Weight in tons ", chunk.literalText);
      assertEquals("0.weight", chunk.fieldName);
      assertEquals("r", chunk.conversion);
      assertEquals("s", chunk.formatSpec);
      chunk = this.newMarkupIterator("{{").nextChunk();
      assertEquals("{", chunk.literalText);
      chunk = this.newMarkupIterator("}}").nextChunk();
      assertEquals("}", chunk.literalText);
      chunk = this.newMarkupIterator("{{}}").nextChunk();
      assertEquals("{}", chunk.literalText);
      chunk = this.newMarkupIterator("{0:.{1}}").nextChunk();
      assertEquals("0", chunk.fieldName);
      assertEquals(".{1}", chunk.formatSpec);
      assertTrue(chunk.formatSpecNeedsExpanding);
      this.assertMarkupError("{!}", "end of format while looking for conversion specifier");
      this.assertMarkupError("{!rrrr}", "expected ':' after conversion specifier");
      this.assertMarkupError("{", "Single '{' encountered in format string");
      this.assertMarkupError("}", "Single '}' encountered in format string");
   }

   public void testMarkupIteratorBytes() {
      this.useBytes = true;
      this.implTestMarkupIterator();
   }

   public void testMarkupIteratorUnicode() {
      this.useBytes = false;
      this.implTestMarkupIterator();
   }

   private MarkupIterator newMarkupIterator(String markup) {
      PyString markupObject = this.useBytes ? Py.newString(markup) : Py.newUnicode(markup);
      return new MarkupIterator((PyString)markupObject);
   }

   private void assertMarkupError(String markup, String expected) {
      MarkupIterator iterator = this.newMarkupIterator(markup);
      String error = null;

      try {
         iterator.nextChunk();
      } catch (IllegalArgumentException var6) {
         error = var6.getMessage();
      }

      assertEquals(expected, error);
   }

   public void implTestFieldNameIterator() {
      FieldNameIterator it = this.newFieldNameIterator("abc");
      assertEquals("abc", it.head());
      assertNull(it.nextChunk());
      it = this.newFieldNameIterator("3");
      assertEquals(3, it.head());
      assertNull(it.nextChunk());
      it = this.newFieldNameIterator("abc[0]");
      assertEquals("abc", it.head());
      FieldNameIterator.Chunk chunk = it.nextChunk();
      assertEquals(0, chunk.value);
      assertFalse(chunk.is_attr);
      assertNull(it.nextChunk());
      it = this.newFieldNameIterator("abc.def");
      assertEquals("abc", it.head());
      chunk = it.nextChunk();
      assertEquals("def", chunk.value);
      assertTrue(chunk.is_attr);
      assertNull(it.nextChunk());
   }

   public void testFieldNameIteratorBytes() {
      this.useBytes = true;
      this.implTestFieldNameIterator();
   }

   public void testFieldNameIteratorUnicode() {
      this.useBytes = false;
      this.implTestFieldNameIterator();
   }

   private FieldNameIterator newFieldNameIterator(String field) {
      PyString fieldObject = this.useBytes ? Py.newString(field) : Py.newUnicode(field);
      return new FieldNameIterator((PyString)fieldObject);
   }
}
