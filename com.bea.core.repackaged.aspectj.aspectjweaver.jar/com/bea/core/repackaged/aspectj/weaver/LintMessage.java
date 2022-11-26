package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;

public class LintMessage extends Message {
   private String lintKind;

   public LintMessage(String message, IMessage.Kind messageKind, ISourceLocation location, ISourceLocation[] extraLocations, Lint.Kind lintKind) {
      super(message, "", messageKind, location, (Throwable)null, extraLocations);
      this.lintKind = lintKind.getName();
   }

   public LintMessage(String message, String extraDetails, Lint.Kind kind2, IMessage.Kind kind, ISourceLocation sourceLocation, Throwable object, ISourceLocation[] seeAlsoLocations, boolean declared, int id, int sourceStart, int sourceEnd) {
      super(message, extraDetails, kind, sourceLocation, object, seeAlsoLocations, declared, id, sourceStart, sourceEnd);
      this.lintKind = kind2.getName();
   }

   public String getLintKind() {
      return this.lintKind;
   }
}
