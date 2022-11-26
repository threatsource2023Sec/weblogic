package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;

public class Parse extends InputBase {
   private boolean ready = false;

   public String getName() {
      return "parse";
   }

   public int getType() {
      return 2;
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
      if (node.jjtGetChild(0) == null) {
         super.rsvc.error("#parse() error :  null argument");
         return false;
      } else {
         Object value = node.jjtGetChild(0).value(context);
         if (value == null) {
            super.rsvc.error("#parse() error :  null argument");
            return false;
         } else {
            String arg = value.toString();
            Object[] templateStack = context.getTemplateNameStack();
            if (templateStack.length >= super.rsvc.getInt("directive.parse.max.depth", 20)) {
               StringBuffer path = new StringBuffer();

               for(int i = 0; i < templateStack.length; ++i) {
                  path.append(" > " + templateStack[i]);
               }

               super.rsvc.error("Max recursion depth reached (" + templateStack.length + ")" + " File stack:" + path);
               return false;
            } else {
               Template t = null;

               try {
                  t = super.rsvc.getTemplate(arg, this.getInputEncoding(context));
               } catch (ResourceNotFoundException var18) {
                  super.rsvc.error("#parse(): cannot find template '" + arg + "', called from template " + context.getCurrentTemplateName() + " at (" + this.getLine() + ", " + this.getColumn() + ")");
                  throw var18;
               } catch (ParseErrorException var19) {
                  super.rsvc.error("#parse(): syntax error in #parse()-ed template '" + arg + "', called from template " + context.getCurrentTemplateName() + " at (" + this.getLine() + ", " + this.getColumn() + ")");
                  throw var19;
               } catch (Exception var20) {
                  super.rsvc.error("#parse() : arg = " + arg + ".  Exception : " + var20);
                  return false;
               }

               boolean var9;
               try {
                  context.pushCurrentTemplateName(arg);
                  ((SimpleNode)t.getData()).render(context, writer);
                  return true;
               } catch (Exception var21) {
                  if (var21 instanceof MethodInvocationException) {
                     throw (MethodInvocationException)var21;
                  }

                  super.rsvc.error("Exception rendering #parse( " + arg + " )  : " + var21);
                  var9 = false;
               } finally {
                  context.popCurrentTemplateName();
               }

               return var9;
            }
         }
      }
   }
}
