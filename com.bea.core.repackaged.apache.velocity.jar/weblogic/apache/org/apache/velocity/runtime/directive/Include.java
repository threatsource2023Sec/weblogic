package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;

public class Include extends InputBase {
   private String outputMsgStart = "";
   private String outputMsgEnd = "";

   public String getName() {
      return "include";
   }

   public int getType() {
      return 2;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      super.init(rs, context, node);
      this.outputMsgStart = super.rsvc.getString("directive.include.output.errormsg.start");
      this.outputMsgStart = this.outputMsgStart + " ";
      this.outputMsgEnd = super.rsvc.getString("directive.include.output.errormsg.end");
      this.outputMsgEnd = " " + this.outputMsgEnd;
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, MethodInvocationException, ResourceNotFoundException {
      int argCount = node.jjtGetNumChildren();

      for(int i = 0; i < argCount; ++i) {
         Node n = node.jjtGetChild(i);
         if (n.getType() != 6 && n.getType() != 14) {
            super.rsvc.error("#include() error : invalid argument type : " + n.toString());
            this.outputErrorToStream(writer, "error with arg " + i + " please see log.");
         } else if (!this.renderOutput(n, context, writer)) {
            this.outputErrorToStream(writer, "error with arg " + i + " please see log.");
         }
      }

      return true;
   }

   private boolean renderOutput(Node node, InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException, ResourceNotFoundException {
      String arg = "";
      if (node == null) {
         super.rsvc.error("#include() error :  null argument");
         return false;
      } else {
         Object value = node.value(context);
         if (value == null) {
            super.rsvc.error("#include() error :  null argument");
            return false;
         } else {
            arg = value.toString();
            Resource resource = null;

            try {
               resource = super.rsvc.getContent(arg, this.getInputEncoding(context));
            } catch (ResourceNotFoundException var9) {
               super.rsvc.error("#include(): cannot find resource '" + arg + "', called from template " + context.getCurrentTemplateName() + " at (" + this.getLine() + ", " + this.getColumn() + ")");
               throw var9;
            } catch (Exception var10) {
               super.rsvc.error("#include(): arg = '" + arg + "', called from template " + context.getCurrentTemplateName() + " at (" + this.getLine() + ", " + this.getColumn() + ") : " + var10);
            }

            if (resource == null) {
               return false;
            } else {
               writer.write((String)resource.getData());
               return true;
            }
         }
      }
   }

   private void outputErrorToStream(Writer writer, String msg) throws IOException {
      if (this.outputMsgStart != null && this.outputMsgEnd != null) {
         writer.write(this.outputMsgStart);
         writer.write(msg);
         writer.write(this.outputMsgEnd);
      }

   }
}
