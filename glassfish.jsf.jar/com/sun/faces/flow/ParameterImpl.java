package com.sun.faces.flow;

import java.io.Serializable;
import javax.el.ValueExpression;
import javax.faces.flow.Parameter;

public class ParameterImpl extends Parameter implements Serializable {
   private static final long serialVersionUID = -5433802753213440653L;
   private String name;
   private ValueExpression value;

   public ParameterImpl() {
   }

   public ParameterImpl(String name, ValueExpression value) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ValueExpression getValue() {
      return this.value;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }
}
