package weblogic.xml.process;

import weblogic.utils.NestedException;

public class XMLProcessingException extends NestedException {
   private static final long serialVersionUID = -9066863319527593494L;
   protected String fileName;

   public XMLProcessingException() {
   }

   public XMLProcessingException(String msg) {
      super(msg);
   }

   public XMLProcessingException(Throwable th) {
      super(th);
   }

   public XMLProcessingException(Throwable th, String fileName) {
      super(th);
      this.fileName = fileName;
   }

   public XMLProcessingException(String msg, Throwable th) {
      super(msg, th);
   }

   public void setFileName(String name) {
      this.fileName = name;
   }

   public String getFileName() {
      return this.fileName;
   }

   public String toString() {
      return this.fileName == null ? super.toString() : "Error processing file '" + this.fileName + "'. " + super.toString();
   }
}
