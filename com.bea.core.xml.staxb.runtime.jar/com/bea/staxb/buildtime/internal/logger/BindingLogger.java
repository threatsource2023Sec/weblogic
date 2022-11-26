package com.bea.staxb.buildtime.internal.logger;

import com.bea.util.jam.JElement;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import java.util.logging.Level;

public class BindingLogger {
   private static final MessageSink DEFAULT_SINK = new SimpleMessageSink();
   public static final BindingLogger DEFAULT = new BindingLogger();
   private boolean mVerbose = false;
   private MessageSink mSink;
   private boolean mAnyErrorsFound;
   private boolean mIgnoreErrors;

   protected BindingLogger(MessageSink sink) {
      this.mSink = DEFAULT_SINK;
      this.mAnyErrorsFound = false;
      this.mIgnoreErrors = false;
      this.mSink = sink;
   }

   protected BindingLogger() {
      this.mSink = DEFAULT_SINK;
      this.mAnyErrorsFound = false;
      this.mIgnoreErrors = false;
      this.mSink = DEFAULT_SINK;
   }

   public void setVerbose(boolean b) {
      this.mVerbose = b;
   }

   public void setIgnoreErrors(boolean b) {
      this.mIgnoreErrors = b;
   }

   public void setMessageSink(MessageSink blp) {
      this.mSink = blp;
   }

   public boolean isAnyErrorsFound() {
      return this.mAnyErrorsFound;
   }

   public boolean isIgnoreErrors() {
      return this.mIgnoreErrors;
   }

   public boolean isVerbose() {
      return this.mVerbose;
   }

   public void logWarning(String msg) {
      this.mSink.log(new MessageImpl(Level.WARNING, msg, (Throwable)null, (JElement)null, (SchemaType)null, (SchemaProperty)null));
   }

   public boolean logError(String msg) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, msg, (Throwable)null, (JElement)null, (SchemaType)null, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(Throwable t) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, (String)null, t, (JElement)null, (SchemaType)null, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(Throwable error, JElement javaContext) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, (String)null, error, javaContext, (SchemaType)null, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(Throwable error, SchemaType schemaContext) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, (String)null, error, (JElement)null, schemaContext, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(Throwable t, JElement jCtx, SchemaType xsdCtx) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, (String)null, t, jCtx, xsdCtx, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(String msg, JElement javaContext) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, msg, (Throwable)null, javaContext, (SchemaType)null, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(String msg, SchemaType xsdCtx) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, msg, (Throwable)null, (JElement)null, xsdCtx, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(String msg, JElement javaCtx, SchemaType xsdCtx) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, msg, (Throwable)null, javaCtx, xsdCtx, (SchemaProperty)null));
      return this.mIgnoreErrors;
   }

   public boolean logError(String msg, JElement jCtx, SchemaProperty xCtx) {
      this.mAnyErrorsFound = true;
      this.mSink.log(new MessageImpl(Level.SEVERE, msg, (Throwable)null, jCtx, (SchemaType)null, xCtx));
      return this.mIgnoreErrors;
   }

   public void logVerbose(String msg) {
      if (this.mVerbose) {
         this.mSink.log(new MessageImpl(Level.FINEST, msg, (Throwable)null, (JElement)null, (SchemaType)null, (SchemaProperty)null));
      }

   }

   public void logVerbose(String msg, JElement javaContext) {
      if (this.mVerbose) {
         this.mSink.log(new MessageImpl(Level.FINEST, msg, (Throwable)null, javaContext, (SchemaType)null, (SchemaProperty)null));
      }

   }

   public void logVerbose(String msg, SchemaType xsdType) {
      if (this.mVerbose) {
         this.mSink.log(new MessageImpl(Level.FINEST, msg, (Throwable)null, (JElement)null, xsdType, (SchemaProperty)null));
      }

   }

   public void logVerbose(String msg, JElement javaCtx, SchemaType xsdCtx) {
      if (this.mVerbose) {
         this.mSink.log(new MessageImpl(Level.FINEST, msg, (Throwable)null, javaCtx, xsdCtx, (SchemaProperty)null));
      }

   }
}
