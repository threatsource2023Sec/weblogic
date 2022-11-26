package com.sun.faces.facelets.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;
import javax.faces.view.facelets.TagAttribute;

public final class TagMethodExpression extends MethodExpression implements Externalizable {
   private static final long serialVersionUID = 1L;
   private String attr;
   private MethodExpression orig;

   public TagMethodExpression() {
   }

   public TagMethodExpression(TagAttribute attr, MethodExpression orig) {
      this.attr = attr.toString();
      this.orig = orig;
   }

   public MethodInfo getMethodInfo(ELContext context) {
      try {
         return this.orig.getMethodInfo(context);
      } catch (PropertyNotFoundException var3) {
         throw new PropertyNotFoundException(this.attr + ": " + var3.getMessage(), var3.getCause());
      } catch (MethodNotFoundException var4) {
         throw new MethodNotFoundException(this.attr + ": " + var4.getMessage(), var4.getCause());
      } catch (ELException var5) {
         throw new ELException(this.attr + ": " + var5.getMessage(), var5.getCause());
      }
   }

   public Object invoke(ELContext context, Object[] params) {
      try {
         return this.orig.invoke(context, params);
      } catch (PropertyNotFoundException var4) {
         throw new PropertyNotFoundException(this.attr + ": " + var4.getMessage(), var4.getCause());
      } catch (MethodNotFoundException var5) {
         throw new MethodNotFoundException(this.attr + ": " + var5.getMessage(), var5.getCause());
      } catch (ELException var6) {
         throw new ELException(this.attr + ": " + var6.getMessage(), var6.getCause());
      }
   }

   public String getExpressionString() {
      return this.orig.getExpressionString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TagMethodExpression that = (TagMethodExpression)o;
         if (this.attr != null) {
            if (!this.attr.equals(that.attr)) {
               return false;
            }
         } else if (that.attr != null) {
            return false;
         }

         if (this.orig != null) {
            if (this.orig.equals(that.orig)) {
               return true;
            }
         } else if (that.orig == null) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.attr != null ? this.attr.hashCode() : 0;
      result = 31 * result + (this.orig != null ? this.orig.hashCode() : 0);
      return result;
   }

   public boolean isLiteralText() {
      return this.orig.isLiteralText();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.orig);
      out.writeUTF(this.attr);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.orig = (MethodExpression)in.readObject();
      this.attr = in.readUTF();
   }

   public String toString() {
      return this.attr + ": " + this.orig;
   }
}
