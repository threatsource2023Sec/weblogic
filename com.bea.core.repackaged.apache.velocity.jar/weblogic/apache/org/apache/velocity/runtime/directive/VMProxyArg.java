package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapterImpl;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.parser.ParserTreeConstants;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTReference;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.util.StringUtils;

public class VMProxyArg {
   private int type = 0;
   private SimpleNode nodeTree = null;
   private Object staticObject = null;
   private InternalContextAdapter usercontext = null;
   private int numTreeChildren = 0;
   private String contextReference = null;
   private String callerReference = null;
   private String singleLevelRef = null;
   private boolean constant = false;
   private final int GENERALSTATIC = -1;
   private RuntimeServices rsvc = null;

   public VMProxyArg(RuntimeServices rs, String contextRef, String callerRef, int t) {
      this.rsvc = rs;
      this.contextReference = contextRef;
      this.callerReference = callerRef;
      this.type = t;
      this.setup();
      if (this.nodeTree != null) {
         this.numTreeChildren = this.nodeTree.jjtGetNumChildren();
      }

      if (this.type == 14 && this.numTreeChildren == 0) {
         this.singleLevelRef = ((ASTReference)this.nodeTree).getRootString();
      }

   }

   public boolean isConstant() {
      return this.constant;
   }

   public Object setObject(InternalContextAdapter context, Object o) {
      if (this.type == 14) {
         if (this.numTreeChildren > 0) {
            try {
               ((ASTReference)this.nodeTree).setValue(context, o);
            } catch (MethodInvocationException var4) {
               this.rsvc.error("VMProxyArg.getObject() : method invocation error setting value : " + var4);
            }
         } else {
            context.put(this.singleLevelRef, o);
         }
      } else {
         this.type = -1;
         this.staticObject = o;
         this.rsvc.error("VMProxyArg.setObject() : Programmer error : I am a constant!  No setting! : " + this.contextReference + " / " + this.callerReference);
      }

      return null;
   }

   public Object getObject(InternalContextAdapter context) {
      try {
         Object retObject = null;
         if (this.type == 14) {
            if (this.numTreeChildren == 0) {
               retObject = context.get(this.singleLevelRef);
            } else {
               retObject = this.nodeTree.execute((Object)null, context);
            }
         } else if (this.type == 11) {
            retObject = this.nodeTree.value(context);
         } else if (this.type == 12) {
            retObject = this.nodeTree.value(context);
         } else if (this.type == 15) {
            retObject = this.staticObject;
         } else if (this.type == 16) {
            retObject = this.staticObject;
         } else if (this.type == 6) {
            retObject = this.nodeTree.value(context);
         } else if (this.type == 5) {
            retObject = this.staticObject;
         } else if (this.type == 17) {
            try {
               StringWriter writer = new StringWriter();
               this.nodeTree.render(context, writer);
               retObject = writer;
            } catch (Exception var4) {
               this.rsvc.error("VMProxyArg.getObject() : error rendering reference : " + var4);
            }
         } else if (this.type == -1) {
            retObject = this.staticObject;
         } else {
            this.rsvc.error("Unsupported VM arg type : VM arg = " + this.callerReference + " type = " + this.type + "( VMProxyArg.getObject() )");
         }

         return retObject;
      } catch (MethodInvocationException var5) {
         this.rsvc.error("VMProxyArg.getObject() : method invocation error getting value : " + var5);
         return null;
      }
   }

   private void setup() {
      switch (this.type) {
         case 5:
            this.constant = true;
            this.staticObject = new Integer(this.callerReference);
            break;
         case 6:
         case 11:
         case 12:
         case 14:
         case 17:
            this.constant = false;

            try {
               String buff = "#include(" + this.callerReference + " ) ";
               BufferedReader br = new BufferedReader(new StringReader(buff));
               this.nodeTree = this.rsvc.parse(br, "VMProxyArg:" + this.callerReference, true);
               this.nodeTree = (SimpleNode)this.nodeTree.jjtGetChild(0).jjtGetChild(0);
               if (this.nodeTree != null && this.nodeTree.getType() != this.type) {
                  this.rsvc.error("VMProxyArg.setup() : programmer error : type doesn't match node type.");
               }

               InternalContextAdapter ica = new InternalContextAdapterImpl(new VelocityContext());
               ica.pushCurrentTemplateName("VMProxyArg : " + ParserTreeConstants.jjtNodeName[this.type]);
               this.nodeTree.init(ica, this.rsvc);
            } catch (Exception var4) {
               this.rsvc.error("VMProxyArg.setup() : exception " + this.callerReference + " : " + StringUtils.stackTrace(var4));
            }
            break;
         case 7:
         case 9:
         case 10:
         case 13:
         default:
            this.rsvc.error(" VMProxyArg.setup() : unsupported type : " + this.callerReference);
            break;
         case 8:
            this.rsvc.error("Unsupported arg type : " + this.callerReference + "  You most likely intended to call a VM with a string literal, so enclose with ' or \" characters. (VMProxyArg.setup())");
            this.constant = true;
            this.staticObject = new String(this.callerReference);
            break;
         case 15:
            this.constant = true;
            this.staticObject = new Boolean(true);
            break;
         case 16:
            this.constant = true;
            this.staticObject = new Boolean(false);
      }

   }

   public VMProxyArg(VMProxyArg model, InternalContextAdapter c) {
      this.usercontext = c;
      this.contextReference = model.getContextReference();
      this.callerReference = model.getCallerReference();
      this.nodeTree = model.getNodeTree();
      this.staticObject = model.getStaticObject();
      this.type = model.getType();
      if (this.nodeTree != null) {
         this.numTreeChildren = this.nodeTree.jjtGetNumChildren();
      }

      if (this.type == 14 && this.numTreeChildren == 0) {
         this.singleLevelRef = ((ASTReference)this.nodeTree).getRootString();
      }

   }

   public String getCallerReference() {
      return this.callerReference;
   }

   public String getContextReference() {
      return this.contextReference;
   }

   public SimpleNode getNodeTree() {
      return this.nodeTree;
   }

   public Object getStaticObject() {
      return this.staticObject;
   }

   public int getType() {
      return this.type;
   }
}
