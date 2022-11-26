package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;

public abstract class Directive implements DirectiveConstants, Cloneable {
   private int line = 0;
   private int column = 0;
   protected RuntimeServices rsvc = null;

   public abstract String getName();

   public abstract int getType();

   public void setLocation(int line, int column) {
      this.line = line;
      this.column = column;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      this.rsvc = rs;
   }

   public abstract boolean render(InternalContextAdapter var1, Writer var2, Node var3) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException;
}
