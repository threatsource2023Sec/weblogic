package com.bea.core.repackaged.aspectj.bridge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Message implements IMessage {
   private final String message;
   private final IMessage.Kind kind;
   private final Throwable thrown;
   private final ISourceLocation sourceLocation;
   private final String details;
   private final List extraSourceLocations;
   private final boolean declared;
   private final int id;
   private final int sourceStart;
   private final int sourceEnd;

   public Message(String message, ISourceLocation location, boolean isError) {
      this(message, isError ? IMessage.ERROR : IMessage.WARNING, (Throwable)null, location);
   }

   public Message(String message, ISourceLocation location, boolean isError, ISourceLocation[] extraSourceLocations) {
      this(message, "", isError ? IMessage.ERROR : IMessage.WARNING, location, (Throwable)null, extraSourceLocations.length > 0 ? extraSourceLocations : null);
   }

   public Message(String message, String details, IMessage.Kind kind, ISourceLocation sourceLocation, Throwable thrown, ISourceLocation[] extraSourceLocations) {
      this(message, details, kind, sourceLocation, thrown, extraSourceLocations, false, 0, -1, -1);
   }

   public Message(String message, String details, IMessage.Kind kind, ISourceLocation sLoc, Throwable thrown, ISourceLocation[] otherLocs, boolean declared, int id, int sourcestart, int sourceend) {
      this.details = details;
      this.id = id;
      this.sourceStart = sourcestart;
      this.sourceEnd = sourceend;
      this.message = message != null ? message : (thrown == null ? null : thrown.getMessage());
      this.kind = kind;
      this.sourceLocation = sLoc;
      this.thrown = thrown;
      if (otherLocs != null) {
         this.extraSourceLocations = Collections.unmodifiableList(Arrays.asList(otherLocs));
      } else {
         this.extraSourceLocations = Collections.emptyList();
      }

      if (null == this.kind) {
         throw new IllegalArgumentException("null kind");
      } else if (null == this.message) {
         throw new IllegalArgumentException("null message");
      } else {
         this.declared = declared;
      }
   }

   public Message(String message, IMessage.Kind kind, Throwable thrown, ISourceLocation sourceLocation) {
      this(message, "", kind, sourceLocation, thrown, (ISourceLocation[])null);
   }

   public IMessage.Kind getKind() {
      return this.kind;
   }

   public boolean isError() {
      return this.kind == IMessage.ERROR;
   }

   public boolean isWarning() {
      return this.kind == IMessage.WARNING;
   }

   public boolean isDebug() {
      return this.kind == IMessage.DEBUG;
   }

   public boolean isTaskTag() {
      return this.kind == IMessage.TASKTAG;
   }

   public boolean isInfo() {
      return this.kind == IMessage.INFO;
   }

   public boolean isAbort() {
      return this.kind == IMessage.ABORT;
   }

   public boolean getDeclared() {
      return this.declared;
   }

   public boolean isFailed() {
      return this.kind == IMessage.FAIL;
   }

   public final String getMessage() {
      return this.message;
   }

   public final Throwable getThrown() {
      return this.thrown;
   }

   public final ISourceLocation getSourceLocation() {
      return this.sourceLocation;
   }

   public String toString() {
      return MessageUtil.renderMessage(this, false);
   }

   public String getDetails() {
      return this.details;
   }

   public List getExtraSourceLocations() {
      return this.extraSourceLocations;
   }

   public int getID() {
      return this.id;
   }

   public int getSourceStart() {
      return this.sourceStart;
   }

   public int getSourceEnd() {
      return this.sourceEnd;
   }
}
