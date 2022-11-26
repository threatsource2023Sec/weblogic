package org.python.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.python.antlr.base.mod;
import org.python.core.BytecodeLoader;
import org.python.core.CompilerFlags;
import org.python.core.PyCode;
import org.python.core.PythonCodeBundle;
import org.python.core.PythonCompiler;

public class LegacyCompiler implements PythonCompiler {
   public PythonCodeBundle compile(mod node, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags) {
      return new LazyLegacyBundle(node, name, filename, linenumbers, printResults, cflags);
   }

   private static class LazyLegacyBundle implements PythonCodeBundle {
      private final mod node;
      private final String name;
      private final String filename;
      private final boolean linenumbers;
      private final boolean printResults;
      private final CompilerFlags cflags;
      private ByteArrayOutputStream ostream = null;

      public LazyLegacyBundle(mod node, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags) {
         this.node = node;
         this.name = name;
         this.filename = filename;
         this.linenumbers = linenumbers;
         this.printResults = printResults;
         this.cflags = cflags;
      }

      public PyCode loadCode() throws Exception {
         return BytecodeLoader.makeCode(this.name, this.ostream().toByteArray(), this.filename);
      }

      public void writeTo(OutputStream stream) throws Exception {
         if (this.ostream != null) {
            stream.write(this.ostream.toByteArray());
         } else {
            Module.compile(this.node, stream, this.name, this.filename, this.linenumbers, this.printResults, this.cflags);
         }

      }

      private ByteArrayOutputStream ostream() throws Exception {
         if (this.ostream == null) {
            this.ostream = new ByteArrayOutputStream();
            Module.compile(this.node, this.ostream, this.name, this.filename, this.linenumbers, this.printResults, this.cflags);
         }

         return this.ostream;
      }
   }
}
