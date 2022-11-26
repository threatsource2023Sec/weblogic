package org.stringtemplate.v4.misc;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.InstanceScope;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;

public class ErrorManager {
   public static STErrorListener DEFAULT_ERROR_LISTENER = new STErrorListener() {
      public void compileTimeError(STMessage msg) {
         System.err.println(msg);
      }

      public void runTimeError(STMessage msg) {
         if (msg.error != ErrorType.NO_SUCH_PROPERTY) {
            System.err.println(msg);
         }

      }

      public void IOError(STMessage msg) {
         System.err.println(msg);
      }

      public void internalError(STMessage msg) {
         System.err.println(msg);
      }

      public void error(String s) {
         this.error(s, (Throwable)null);
      }

      public void error(String s, Throwable e) {
         System.err.println(s);
         if (e != null) {
            e.printStackTrace(System.err);
         }

      }
   };
   public final STErrorListener listener;

   public ErrorManager() {
      this(DEFAULT_ERROR_LISTENER);
   }

   public ErrorManager(STErrorListener listener) {
      this.listener = listener;
   }

   public void compileTimeError(ErrorType error, Token templateToken, Token t) {
      CharStream input = t.getInputStream();
      String srcName = null;
      if (input != null) {
         srcName = input.getSourceName();
         if (srcName != null) {
            srcName = Misc.getFileName(srcName);
         }
      }

      this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, (Throwable)null, t.getText()));
   }

   public void lexerError(String srcName, String msg, Token templateToken, RecognitionException e) {
      if (srcName != null) {
         srcName = Misc.getFileName(srcName);
      }

      this.listener.compileTimeError(new STLexerMessage(srcName, msg, templateToken, e));
   }

   public void compileTimeError(ErrorType error, Token templateToken, Token t, Object arg) {
      String srcName = t.getInputStream().getSourceName();
      if (srcName != null) {
         srcName = Misc.getFileName(srcName);
      }

      this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, (Throwable)null, arg));
   }

   public void compileTimeError(ErrorType error, Token templateToken, Token t, Object arg, Object arg2) {
      String srcName = t.getInputStream().getSourceName();
      if (srcName != null) {
         srcName = Misc.getFileName(srcName);
      }

      this.listener.compileTimeError(new STCompiletimeMessage(error, srcName, templateToken, t, (Throwable)null, arg, arg2));
   }

   public void groupSyntaxError(ErrorType error, String srcName, RecognitionException e, String msg) {
      Token t = e.token;
      this.listener.compileTimeError(new STGroupCompiletimeMessage(error, srcName, e.token, e, msg));
   }

   public void groupLexerError(ErrorType error, String srcName, RecognitionException e, String msg) {
      this.listener.compileTimeError(new STGroupCompiletimeMessage(error, srcName, e.token, e, msg));
   }

   public void runTimeError(Interpreter interp, InstanceScope scope, ErrorType error) {
      this.listener.runTimeError(new STRuntimeMessage(interp, error, scope != null ? scope.ip : 0, scope));
   }

   public void runTimeError(Interpreter interp, InstanceScope scope, ErrorType error, Object arg) {
      this.listener.runTimeError(new STRuntimeMessage(interp, error, scope != null ? scope.ip : 0, scope, arg));
   }

   public void runTimeError(Interpreter interp, InstanceScope scope, ErrorType error, Throwable e, Object arg) {
      this.listener.runTimeError(new STRuntimeMessage(interp, error, scope != null ? scope.ip : 0, scope, e, arg));
   }

   public void runTimeError(Interpreter interp, InstanceScope scope, ErrorType error, Object arg, Object arg2) {
      this.listener.runTimeError(new STRuntimeMessage(interp, error, scope != null ? scope.ip : 0, scope, (Throwable)null, arg, arg2));
   }

   public void runTimeError(Interpreter interp, InstanceScope scope, ErrorType error, Object arg, Object arg2, Object arg3) {
      this.listener.runTimeError(new STRuntimeMessage(interp, error, scope != null ? scope.ip : 0, scope, (Throwable)null, arg, arg2, arg3));
   }

   public void IOError(ST self, ErrorType error, Throwable e) {
      this.listener.IOError(new STMessage(error, self, e));
   }

   public void IOError(ST self, ErrorType error, Throwable e, Object arg) {
      this.listener.IOError(new STMessage(error, self, e, arg));
   }

   public void internalError(ST self, String msg, Throwable e) {
      this.listener.internalError(new STMessage(ErrorType.INTERNAL_ERROR, self, e, msg));
   }
}
