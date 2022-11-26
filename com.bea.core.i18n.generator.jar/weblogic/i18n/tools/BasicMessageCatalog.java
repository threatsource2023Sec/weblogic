package weblogic.i18n.tools;

import java.util.Vector;

public class BasicMessageCatalog extends Element {
   public static final String DEFAULT_ENCODING = "UTF-8";
   public static final int UNKNOWN = 0;
   public static final int SIMPLE = 1;
   public static final int LOG = 2;
   public static final int EXCEPTION = 3;
   static final String[] types = new String[]{"Unknown", "Simple", "Log", "Exception"};
   protected static final String VERSION = "1.0";
   protected Config cfg;
   private String filePathAndName = null;
   private String comment = null;
   protected String version = null;
   protected String filename = null;
   protected Vector messages = new Vector();
   protected Vector logMessages = new Vector();
   protected Vector exceptions = new Vector();
   private Vector[] allMessages;
   String inputEncoding;
   String outputEncoding;
   protected int catType;

   public BasicMessageCatalog() {
      this.allMessages = new Vector[]{this.messages, this.logMessages, this.exceptions};
      this.inputEncoding = "UTF-8";
      this.outputEncoding = "UTF-8";
      this.catType = 0;
   }

   public String getInputEncoding() {
      return this.inputEncoding;
   }

   public void setInputEncoding(String v) {
      if (v != null) {
         this.inputEncoding = v;
      }

   }

   public String getOutputEncoding() {
      return this.outputEncoding;
   }

   public void setOutputEncoding(String v) {
      if (v != null) {
         this.outputEncoding = v;
      }

   }

   public int getCatType() {
      return this.catType;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String cmt) {
      this.comment = cmt;
   }

   public Vector getLogMessages() {
      return this.logMessages;
   }

   public Vector getMessages() {
      return this.messages;
   }

   public Vector getExceptions() {
      return this.exceptions;
   }

   public Vector[] getAllMessages() {
      return this.allMessages;
   }

   public String getFileName() {
      return this.filename;
   }

   public String getVersion() {
      return this.version;
   }

   public String getPath() {
      return this.filePathAndName;
   }

   public void setPath(String path) {
      this.filePathAndName = path;
   }

   public boolean verifyMessageCatalog(int type) throws WrongTypeException {
      if (this.catType == 0) {
         this.catType = type;
      }

      if (this.catType != type) {
         throw new WrongTypeException("Unexpected message type. Should be " + types[type]);
      } else {
         return true;
      }
   }

   public BasicMessage findMessage(String id) {
      BasicMessage msg;
      if (this.catType == 1) {
         msg = this.findMessage(id, this.messages);
      } else if (this.catType == 2) {
         msg = this.findMessage(id, this.logMessages);
      } else {
         msg = this.findMessage(id, this.messages);
         if (msg == null) {
            msg = this.findMessage(id, this.logMessages);
         }
      }

      return msg;
   }

   private BasicMessage findMessage(String id, Vector messages) {
      int el = 0;

      for(int len = messages.size(); el < len; ++el) {
         BasicMessage msg = (BasicMessage)messages.elementAt(el);
         if (msg.getMessageId().equals(id)) {
            return msg;
         }
      }

      return null;
   }
}
