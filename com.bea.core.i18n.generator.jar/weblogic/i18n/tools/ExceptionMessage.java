package weblogic.i18n.tools;

public final class ExceptionMessage extends BasicMessage {
   public static final String attr_exceptionid = "exceptionid";
   public static final String attr_name = "name";
   public static final String attr_extends = "extends";
   private String exceptionid = null;
   private String name = null;
   private String xtends = null;
   private MessageBody messageBody;
   private Object parent = null;

   public Object getParent() {
      return this.parent;
   }

   public void setParent(Object o) {
      this.parent = o;
   }

   public String get(String att) {
      if (att.equals("exceptionid")) {
         return this.exceptionid;
      } else if (att.equals("name")) {
         return this.name;
      } else {
         return att.equals("extends") ? this.xtends : null;
      }
   }

   public void set(String att, String val) {
      if (att.equals("exceptionid")) {
         this.exceptionid = val;
      } else if (att.equals("name")) {
         this.name = val;
      } else if (att.equals("extends")) {
         this.xtends = val;
      }
   }

   public void addMessageBody(MessageBody body) {
      this.messageBody = body;
   }

   public MessageBody getMessageBody() {
      return this.messageBody;
   }

   public String toXML(boolean fmtCData) {
      return "";
   }

   protected String makeDateHash() {
      return "";
   }
}
