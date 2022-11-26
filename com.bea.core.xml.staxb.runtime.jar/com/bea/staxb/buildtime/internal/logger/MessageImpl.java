package com.bea.staxb.buildtime.internal.logger;

import com.bea.util.jam.JElement;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class MessageImpl implements Message {
   private Level mLevel;
   private String mMessage = null;
   private Throwable mException = null;
   private JElement mJavaContext = null;
   private SchemaType mSchemaTypeContext = null;
   private SchemaProperty mSchemaPropertyContext = null;

   public MessageImpl(Level level, String message, Throwable exception, JElement javaContext, SchemaType schemaTypeContext, SchemaProperty schemaPropertyContext) {
      if (level == null) {
         throw new IllegalArgumentException("null level");
      } else {
         this.mLevel = level;
         this.mMessage = message;
         this.mException = exception;
         this.mJavaContext = javaContext;
         this.mSchemaTypeContext = schemaTypeContext;
         this.mSchemaPropertyContext = schemaPropertyContext;
      }
   }

   public Level getLevel() {
      return this.mLevel;
   }

   public String getMessage() {
      if (this.mMessage != null) {
         return this.mMessage;
      } else {
         return this.mException != null ? this.mException.getMessage() : this.mLevel.getLocalizedName();
      }
   }

   public Throwable getException() {
      return this.mException;
   }

   public JElement getJavaContext() {
      return this.mJavaContext;
   }

   public SchemaProperty getSchemaPropertyContext() {
      return this.mSchemaPropertyContext;
   }

   public SchemaType getSchemaTypeContext() {
      return this.mSchemaTypeContext;
   }

   public String toString() {
      StringWriter sw = new StringWriter();
      this.print(new PrintWriter(sw));
      return sw.toString();
   }

   private void print(PrintWriter out) {
      out.print('[');
      out.print(this.mLevel.toString());
      out.print("] ");
      if (this.mMessage != null) {
         out.println(this.mMessage);
      }

      if (this.mJavaContext != null) {
         out.print(" on Java element '");
         out.print(this.mJavaContext.getQualifiedName());
         out.print("'");
      }

      if (this.mSchemaTypeContext != null) {
         out.print(" on Schema type ");
         out.print(this.mSchemaTypeContext.getName());
         out.print("'");
      }

      if (this.mSchemaPropertyContext != null) {
         out.print(" on Schema type ");
         out.print(this.mSchemaPropertyContext.getName());
         out.print("'");
      }

      if (this.mException != null) {
         this.mException.printStackTrace(out);
      }

   }
}
