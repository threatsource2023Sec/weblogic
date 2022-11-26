package org.antlr.tool;

import org.stringtemplate.v4.ST;

public abstract class Message {
   public ST msgST;
   public ST locationST;
   public ST reportST;
   public ST messageFormatST;
   public int msgID;
   public Object arg;
   public Object arg2;
   public Throwable e;
   public String file;
   public int line;
   public int column;

   public Message() {
      this.line = -1;
      this.column = -1;
   }

   public Message(int msgID) {
      this(msgID, (Object)null, (Object)null);
   }

   public Message(int msgID, Object arg, Object arg2) {
      this.line = -1;
      this.column = -1;
      this.setMessageID(msgID);
      this.arg = arg;
      this.arg2 = arg2;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public void setColumn(int column) {
      this.column = column;
   }

   public void setMessageID(int msgID) {
      this.msgID = msgID;
      this.msgST = ErrorManager.getMessage(msgID);
   }

   public ST getMessageTemplate() {
      return new ST(this.msgST);
   }

   public ST getLocationTemplate() {
      return new ST(this.locationST);
   }

   public String toString(ST messageST) {
      this.locationST = ErrorManager.getLocationFormat();
      this.reportST = ErrorManager.getReportFormat();
      this.messageFormatST = ErrorManager.getMessageFormat();
      boolean locationValid = false;
      if (this.line != -1) {
         this.locationST.add("line", this.line);
         locationValid = true;
      }

      if (this.column != -1) {
         this.locationST.add("column", this.column + 1);
         locationValid = true;
      }

      if (this.file != null) {
         this.locationST.add("file", this.file);
         locationValid = true;
      }

      this.messageFormatST.add("id", this.msgID);
      this.messageFormatST.add("text", messageST);
      if (locationValid) {
         this.reportST.add("location", this.locationST);
      }

      this.reportST.add("message", this.messageFormatST);
      this.reportST.add("type", ErrorManager.getMessageType(this.msgID));
      return this.reportST.render();
   }
}
