package com.sun.faces.facelets.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.faces.view.facelets.TagAttribute;

public final class TagValueExpression extends ValueExpression implements Externalizable {
   private static final long serialVersionUID = 1L;
   private ValueExpression wrapped;
   private String tagAttribute;

   public TagValueExpression() {
   }

   public TagValueExpression(TagAttribute tagAttribute, ValueExpression wrapped) {
      this.tagAttribute = tagAttribute.toString();
      this.wrapped = wrapped;
   }

   public Class getExpectedType() {
      return this.wrapped.getExpectedType();
   }

   public Class getType(ELContext context) {
      try {
         return this.wrapped.getType(context);
      } catch (PropertyNotFoundException var3) {
         throw new PropertyNotFoundException(this.tagAttribute + ": " + var3.getMessage(), var3);
      } catch (ELException var4) {
         throw new ELException(this.tagAttribute + ": " + var4.getMessage(), var4);
      }
   }

   public Object getValue(ELContext context) {
      try {
         return this.wrapped.getValue(context);
      } catch (PropertyNotFoundException var3) {
         throw new PropertyNotFoundException(this.tagAttribute + ": " + var3.getMessage(), var3);
      } catch (ELException var4) {
         throw new ELException(this.tagAttribute + ": " + var4.getMessage(), var4);
      }
   }

   public boolean isReadOnly(ELContext context) {
      try {
         return this.wrapped.isReadOnly(context);
      } catch (PropertyNotFoundException var3) {
         throw new PropertyNotFoundException(this.tagAttribute + ": " + var3.getMessage(), var3);
      } catch (ELException var4) {
         throw new ELException(this.tagAttribute + ": " + var4.getMessage(), var4);
      }
   }

   public void setValue(ELContext context, Object value) {
      try {
         this.wrapped.setValue(context, value);
      } catch (PropertyNotFoundException var4) {
         throw new PropertyNotFoundException(this.tagAttribute + ": " + var4.getMessage(), var4);
      } catch (PropertyNotWritableException var5) {
         throw new PropertyNotWritableException(this.tagAttribute + ": " + var5.getMessage(), var5);
      } catch (ELException var6) {
         throw new ELException(this.tagAttribute + ": " + var6.getMessage(), var6);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TagValueExpression that = (TagValueExpression)o;
         if (this.tagAttribute != null) {
            if (!this.tagAttribute.equals(that.tagAttribute)) {
               return false;
            }
         } else if (that.tagAttribute != null) {
            return false;
         }

         if (this.wrapped != null) {
            if (this.wrapped.equals(that.wrapped)) {
               return true;
            }
         } else if (that.wrapped == null) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.wrapped != null ? this.wrapped.hashCode() : 0;
      result = 31 * result + (this.tagAttribute != null ? this.tagAttribute.hashCode() : 0);
      return result;
   }

   public String getExpressionString() {
      return this.wrapped.getExpressionString();
   }

   public boolean isLiteralText() {
      return this.wrapped.isLiteralText();
   }

   public ValueReference getValueReference(ELContext context) {
      return this.wrapped.getValueReference(context);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.wrapped = (ValueExpression)in.readObject();
      this.tagAttribute = in.readUTF();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.wrapped);
      out.writeUTF(this.tagAttribute);
   }

   public ValueExpression getWrapped() {
      return this.wrapped;
   }

   public String toString() {
      return this.tagAttribute;
   }
}
