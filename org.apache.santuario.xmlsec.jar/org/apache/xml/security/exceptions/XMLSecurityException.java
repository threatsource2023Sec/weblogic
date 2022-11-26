package org.apache.xml.security.exceptions;

import java.text.MessageFormat;
import org.apache.xml.security.utils.I18n;

public class XMLSecurityException extends Exception {
   private static final long serialVersionUID = 1L;
   protected String msgID;

   public XMLSecurityException() {
      super("Missing message string");
      this.msgID = null;
   }

   public XMLSecurityException(String msgID) {
      super(I18n.getExceptionMessage(msgID));
      this.msgID = msgID;
   }

   public XMLSecurityException(String msgID, Object[] exArgs) {
      super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs));
      this.msgID = msgID;
   }

   public XMLSecurityException(Exception originalException) {
      super(originalException.getMessage(), originalException);
   }

   public XMLSecurityException(Exception originalException, String msgID) {
      super(I18n.getExceptionMessage(msgID, originalException), originalException);
      this.msgID = msgID;
   }

   /** @deprecated */
   @Deprecated
   public XMLSecurityException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public XMLSecurityException(Exception originalException, String msgID, Object[] exArgs) {
      super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs), originalException);
      this.msgID = msgID;
   }

   /** @deprecated */
   @Deprecated
   public XMLSecurityException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }

   public String getMsgID() {
      return this.msgID == null ? "Missing message ID" : this.msgID;
   }

   public String toString() {
      String s = this.getClass().getName();
      String message = super.getLocalizedMessage();
      if (message != null) {
         message = s + ": " + message;
      } else {
         message = s;
      }

      if (super.getCause() != null) {
         message = message + "\nOriginal Exception was " + super.getCause().toString();
      }

      return message;
   }

   public void printStackTrace() {
      synchronized(System.err) {
         super.printStackTrace(System.err);
      }
   }

   public Exception getOriginalException() {
      return this.getCause() instanceof Exception ? (Exception)this.getCause() : null;
   }
}
