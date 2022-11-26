package com.trilead.ssh2;

import com.trilead.ssh2.sftp.ErrorCodes;
import java.io.IOException;

public class SFTPException extends IOException {
   private static final long serialVersionUID = 578654644222421811L;
   private final String sftpErrorMessage;
   private final int sftpErrorCode;

   private static String constructMessage(String s, int errorCode) {
      String[] detail = ErrorCodes.getDescription(errorCode);
      return detail == null ? s + " (UNKNOW SFTP ERROR CODE)" : s + " (" + detail[0] + ": " + detail[1] + ")";
   }

   SFTPException(String msg, int errorCode) {
      super(constructMessage(msg, errorCode));
      this.sftpErrorMessage = msg;
      this.sftpErrorCode = errorCode;
   }

   public String getServerErrorMessage() {
      return this.sftpErrorMessage;
   }

   public int getServerErrorCode() {
      return this.sftpErrorCode;
   }

   public String getServerErrorCodeSymbol() {
      String[] detail = ErrorCodes.getDescription(this.sftpErrorCode);
      return detail == null ? "UNKNOW SFTP ERROR CODE " + this.sftpErrorCode : detail[0];
   }

   public String getServerErrorCodeVerbose() {
      String[] detail = ErrorCodes.getDescription(this.sftpErrorCode);
      return detail == null ? "The error code " + this.sftpErrorCode + " is unknown." : detail[1];
   }
}
