package com.sun.faces.facelets.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

/** @deprecated */
public final class LegacyValueBinding extends ValueBinding implements Externalizable {
   private static final long serialVersionUID = 1L;
   private ValueExpression delegate;

   public LegacyValueBinding() {
   }

   public LegacyValueBinding(ValueExpression ve) {
      this.delegate = ve;
   }

   public Object getValue(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      ELContext ctx = context.getELContext();

      try {
         return this.delegate.getValue(ctx);
      } catch (javax.el.PropertyNotFoundException var4) {
         throw new PropertyNotFoundException(var4.getMessage(), var4.getCause());
      } catch (ELException var5) {
         throw new EvaluationException(var5.getMessage(), var5.getCause());
      }
   }

   public void setValue(FacesContext context, Object value) throws EvaluationException, PropertyNotFoundException {
      ELContext ctx = context.getELContext();

      try {
         this.delegate.setValue(ctx, value);
      } catch (javax.el.PropertyNotFoundException | PropertyNotWritableException var5) {
         throw new PropertyNotFoundException(var5.getMessage(), var5.getCause());
      } catch (ELException var6) {
         throw new EvaluationException(var6.getMessage(), var6.getCause());
      }
   }

   public boolean isReadOnly(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      ELContext ctx = context.getELContext();

      try {
         return this.delegate.isReadOnly(ctx);
      } catch (javax.el.PropertyNotFoundException var4) {
         throw new PropertyNotFoundException(var4.getMessage(), var4.getCause());
      } catch (ELException var5) {
         throw new EvaluationException(var5.getMessage(), var5.getCause());
      }
   }

   public Class getType(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      ELContext ctx = context.getELContext();

      try {
         return this.delegate.getType(ctx);
      } catch (javax.el.PropertyNotFoundException var4) {
         throw new PropertyNotFoundException(var4.getMessage(), var4.getCause());
      } catch (ELException var5) {
         throw new EvaluationException(var5.getMessage(), var5.getCause());
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.delegate = (ValueExpression)in.readObject();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.delegate);
   }

   public String getExpressionString() {
      return this.delegate.getExpressionString();
   }
}
