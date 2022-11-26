package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.context.VMContext;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.parser.Token;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.runtime.visitor.VMReferenceMungeVisitor;
import weblogic.apache.org.apache.velocity.util.StringUtils;

public class VelocimacroProxy extends Directive {
   private String macroName = "";
   private String macroBody = "";
   private String[] argArray = null;
   private SimpleNode nodeTree = null;
   private int numMacroArgs = 0;
   private String namespace = "";
   private boolean init = false;
   private String[] callingArgs;
   private int[] callingArgTypes;
   private HashMap proxyArgHash = new HashMap();

   public String getName() {
      return this.macroName;
   }

   public int getType() {
      return 2;
   }

   public void setName(String name) {
      this.macroName = name;
   }

   public void setArgArray(String[] arr) {
      this.argArray = arr;
      this.numMacroArgs = this.argArray.length - 1;
   }

   public void setNodeTree(SimpleNode tree) {
      this.nodeTree = tree;
   }

   public int getNumArgs() {
      return this.numMacroArgs;
   }

   public void setMacrobody(String mb) {
      this.macroBody = mb;
   }

   public void setNamespace(String ns) {
      this.namespace = ns;
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, MethodInvocationException {
      try {
         if (this.nodeTree == null) {
            super.rsvc.error("VM error : " + this.macroName + ". Null AST");
         } else {
            if (!this.init) {
               this.nodeTree.init(context, super.rsvc);
               this.init = true;
            }

            VMContext vmc = new VMContext(context, super.rsvc);
            int i = 1;

            while(true) {
               if (i >= this.argArray.length) {
                  this.nodeTree.render(vmc, writer);
                  break;
               }

               VMProxyArg arg = (VMProxyArg)this.proxyArgHash.get(this.argArray[i]);
               vmc.addVMProxyArg(arg);
               ++i;
            }
         }
      } catch (Exception var7) {
         if (var7 instanceof MethodInvocationException) {
            throw (MethodInvocationException)var7;
         }

         super.rsvc.error("VelocimacroProxy.render() : exception VM = #" + this.macroName + "() : " + StringUtils.stackTrace(var7));
      }

      return true;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      super.init(rs, context, node);
      int i = node.jjtGetNumChildren();
      if (this.getNumArgs() != i) {
         super.rsvc.error("VM #" + this.macroName + ": error : too " + (this.getNumArgs() > i ? "few" : "many") + " arguments to macro. Wanted " + this.getNumArgs() + " got " + i);
      } else {
         this.callingArgs = this.getArgArray(node);
         this.setupMacro(this.callingArgs, this.callingArgTypes);
      }
   }

   public boolean setupMacro(String[] callArgs, int[] callArgTypes) {
      this.setupProxyArgs(callArgs, callArgTypes);
      this.parseTree(callArgs);
      return true;
   }

   private void parseTree(String[] callArgs) {
      try {
         BufferedReader br = new BufferedReader(new StringReader(this.macroBody));
         this.nodeTree = super.rsvc.parse(br, this.namespace, false);
         HashMap hm = new HashMap();

         for(int i = 1; i < this.argArray.length; ++i) {
            String arg = callArgs[i - 1];
            if (arg.charAt(0) == '$') {
               hm.put(this.argArray[i], arg);
            }
         }

         VMReferenceMungeVisitor v = new VMReferenceMungeVisitor(hm);
         this.nodeTree.jjtAccept(v, (Object)null);
      } catch (Exception var6) {
         super.rsvc.error("VelocimacroManager.parseTree() : exception " + this.macroName + " : " + StringUtils.stackTrace(var6));
      }

   }

   private void setupProxyArgs(String[] callArgs, int[] callArgTypes) {
      for(int i = 1; i < this.argArray.length; ++i) {
         VMProxyArg arg = new VMProxyArg(super.rsvc, this.argArray[i], callArgs[i - 1], callArgTypes[i - 1]);
         this.proxyArgHash.put(this.argArray[i], arg);
      }

   }

   private String[] getArgArray(Node node) {
      int numArgs = node.jjtGetNumChildren();
      String[] args = new String[numArgs];
      this.callingArgTypes = new int[numArgs];
      int i = 0;
      Token t = null;

      for(Token tLast = null; i < numArgs; ++i) {
         args[i] = "";
         this.callingArgTypes[i] = node.jjtGetChild(i).getType();
         t = node.jjtGetChild(i).getFirstToken();

         for(tLast = node.jjtGetChild(i).getLastToken(); t != tLast; t = t.next) {
            args[i] = args[i] + t.image;
         }

         args[i] = args[i] + t.image;
      }

      return args;
   }
}
