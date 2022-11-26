package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import weblogic.apache.org.apache.velocity.app.event.EventCartridge;
import weblogic.apache.org.apache.velocity.context.Context;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.exception.ReferenceException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.runtime.parser.Token;
import weblogic.apache.org.apache.velocity.util.introspection.Info;
import weblogic.apache.org.apache.velocity.util.introspection.VelPropertySet;

public class ASTReference extends SimpleNode {
   private static final int NORMAL_REFERENCE = 1;
   private static final int FORMAL_REFERENCE = 2;
   private static final int QUIET_REFERENCE = 3;
   private static final int RUNT = 4;
   private int referenceType;
   private String nullString;
   private String rootString;
   private boolean escaped = false;
   private boolean computableReference = true;
   private String escPrefix = "";
   private String morePrefix = "";
   private String identifier = "";
   private String literal = null;
   private int numChildren = 0;
   protected Info uberInfo;

   public ASTReference(int id) {
      super(id);
   }

   public ASTReference(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.rootString = this.getRoot();
      this.numChildren = this.jjtGetNumChildren();
      if (this.numChildren > 0) {
         this.identifier = this.jjtGetChild(this.numChildren - 1).getFirstToken().image;
      }

      this.uberInfo = new Info(context.getCurrentTemplateName(), this.getLine(), this.getColumn());
      return data;
   }

   public String getRootString() {
      return this.rootString;
   }

   public Object execute(Object o, InternalContextAdapter context) throws MethodInvocationException {
      if (this.referenceType == 4) {
         return null;
      } else {
         Object result = this.getVariableValue(context, this.rootString);
         if (result == null) {
            return null;
         } else {
            try {
               for(int i = 0; i < this.numChildren; ++i) {
                  result = this.jjtGetChild(i).execute(result, context);
                  if (result == null) {
                     return null;
                  }
               }

               return result;
            } catch (MethodInvocationException var5) {
               super.rsvc.error("Method " + var5.getMethodName() + " threw exception for reference $" + this.rootString + " in template " + context.getCurrentTemplateName() + " at " + " [" + this.getLine() + "," + this.getColumn() + "]");
               var5.setReferenceName(this.rootString);
               throw var5;
            }
         }
      }
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException {
      if (this.referenceType == 4) {
         writer.write(this.rootString);
         return true;
      } else {
         Object value = this.execute((Object)null, context);
         if (this.escaped) {
            if (value == null) {
               writer.write(this.escPrefix);
               writer.write("\\");
               writer.write(this.nullString);
            } else {
               writer.write(this.escPrefix);
               writer.write(this.nullString);
            }

            return true;
         } else {
            EventCartridge ec = context.getEventCartridge();
            if (ec != null) {
               value = ec.referenceInsert(this.literal(), value);
            }

            if (value == null) {
               writer.write(this.escPrefix);
               writer.write(this.escPrefix);
               writer.write(this.morePrefix);
               writer.write(this.nullString);
               if (this.referenceType != 3 && super.rsvc.getBoolean("runtime.log.invalid.references", true)) {
                  super.rsvc.warn(new ReferenceException("reference : template = " + context.getCurrentTemplateName(), this));
               }

               return true;
            } else {
               writer.write(this.escPrefix);
               writer.write(this.morePrefix);
               writer.write(value.toString());
               return true;
            }
         }
      }
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      Object value = this.execute((Object)null, context);
      if (value == null) {
         return false;
      } else if (value instanceof Boolean) {
         return (Boolean)value;
      } else {
         return true;
      }
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      return this.computableReference ? this.execute((Object)null, context) : null;
   }

   public boolean setValue(InternalContextAdapter context, Object value) throws MethodInvocationException {
      if (this.jjtGetNumChildren() == 0) {
         context.put(this.rootString, value);
         return true;
      } else {
         Object result = this.getVariableValue(context, this.rootString);
         if (result == null) {
            super.rsvc.error(new ReferenceException("reference set : template = " + context.getCurrentTemplateName(), this));
            return false;
         } else {
            for(int i = 0; i < this.numChildren - 1; ++i) {
               result = this.jjtGetChild(i).execute(result, context);
               if (result == null) {
                  super.rsvc.error(new ReferenceException("reference set : template = " + context.getCurrentTemplateName(), this));
                  return false;
               }
            }

            try {
               VelPropertySet vs = super.rsvc.getUberspect().getPropertySet(result, this.identifier, value, this.uberInfo);
               if (vs == null) {
                  return false;
               } else {
                  vs.invoke(result, value);
                  return true;
               }
            } catch (InvocationTargetException var7) {
               throw new MethodInvocationException("ASTReference : Invocation of method '" + this.identifier + "' in  " + result.getClass() + " threw exception " + var7.getTargetException().getClass(), var7.getTargetException(), this.identifier);
            } catch (Exception var8) {
               super.rsvc.error("ASTReference setValue() : exception : " + var8 + " template = " + context.getCurrentTemplateName() + " [" + this.getLine() + "," + this.getColumn() + "]");
               return false;
            }
         }
      }
   }

   private String getRoot() {
      Token t = this.getFirstToken();
      int slashbang = t.image.indexOf("\\!");
      int i;
      int len;
      if (slashbang != -1) {
         int i = false;
         len = t.image.length();
         i = t.image.indexOf(36);
         if (i == -1) {
            super.rsvc.error("ASTReference.getRoot() : internal error : no $ found for slashbang.");
            this.computableReference = false;
            this.nullString = t.image;
            return this.nullString;
         } else {
            while(i < len && t.image.charAt(i) != '\\') {
               ++i;
            }

            int start = i;

            int count;
            for(count = 0; i < len && t.image.charAt(i++) == '\\'; ++count) {
            }

            this.nullString = t.image.substring(0, start);
            this.nullString = this.nullString + t.image.substring(start, start + count - 1);
            this.nullString = this.nullString + t.image.substring(start + count);
            this.computableReference = false;
            return this.nullString;
         }
      } else {
         this.escaped = false;
         if (t.image.startsWith("\\")) {
            i = 0;

            for(len = t.image.length(); i < len && t.image.charAt(i) == '\\'; ++i) {
            }

            if (i % 2 != 0) {
               this.escaped = true;
            }

            if (i > 0) {
               this.escPrefix = t.image.substring(0, i / 2);
            }

            t.image = t.image.substring(i);
         }

         i = t.image.lastIndexOf(36);
         if (i > 0) {
            this.morePrefix = this.morePrefix + t.image.substring(0, i);
            t.image = t.image.substring(i);
         }

         this.nullString = this.literal();
         if (t.image.startsWith("$!")) {
            this.referenceType = 3;
            if (!this.escaped) {
               this.nullString = "";
            }

            return t.image.startsWith("$!{") ? t.next.image : t.image.substring(2);
         } else if (t.image.equals("${")) {
            this.referenceType = 2;
            return t.next.image;
         } else if (t.image.startsWith("$")) {
            this.referenceType = 1;
            return t.image.substring(1);
         } else {
            this.referenceType = 4;
            return t.image;
         }
      }
   }

   public Object getVariableValue(Context context, String variable) {
      return context.get(variable);
   }

   public void setLiteral(String literal) {
      if (this.literal == null) {
         this.literal = literal;
      }

   }

   public String literal() {
      return this.literal != null ? this.literal : super.literal();
   }
}
