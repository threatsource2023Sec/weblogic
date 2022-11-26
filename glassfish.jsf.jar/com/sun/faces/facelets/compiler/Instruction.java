package com.sun.faces.facelets.compiler;

import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

interface Instruction {
   void write(FacesContext var1) throws IOException;

   Instruction apply(ExpressionFactory var1, ELContext var2);

   boolean isLiteral();
}
