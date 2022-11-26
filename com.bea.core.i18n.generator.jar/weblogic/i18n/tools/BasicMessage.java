package weblogic.i18n.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public abstract class BasicMessage extends Element {
   private static final boolean debug = false;
   public static final String attr_messageid = "messageid";
   public static final String attr_date_last_changed = "datelastchanged";
   public static final String attr_date_hash = "datehash";
   public static final String attr_method = "method";
   public static final String attr_retire = "retired";
   protected String messageid = null;
   protected String comment = null;
   protected String datelastchanged = null;
   protected String datehash = null;
   protected String methodName = null;
   protected String[] arguments = null;
   protected String[] argNames = null;
   protected String method = null;
   protected boolean retire = false;
   protected MessageBody messageBody;
   protected Object parent = null;

   public String getMessageId() {
      return this.messageid;
   }

   public String getDateLastChanged() {
      return this.datelastchanged;
   }

   public String getDateHash() {
      return this.datehash;
   }

   public String getComment() {
      return this.comment;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String[] getArguments() {
      return this.arguments;
   }

   public String[] getArgNames() {
      return this.argNames;
   }

   public String getMethod() {
      return this.method;
   }

   public boolean isRetired() {
      return this.retire;
   }

   protected abstract String makeDateHash();

   public boolean hashesOK() {
      return this.datehash == null ? false : this.datehash.equals(this.makeDateHash());
   }

   public void setRetired(String retired) {
      this.retire = "true".equals(retired);
   }

   public void setRetired(boolean isRetired) {
      this.debug("Retiring " + this.messageid);
      this.retire = isRetired;
   }

   public void setComment(String cmt) {
      this.comment = cmt;
   }

   public void setDateLastChanged(String longDate) {
      this.datelastchanged = longDate;
   }

   public Object getParent() {
      return this.parent;
   }

   public void setParent(Object mc) {
      this.parent = mc;
   }

   public String get(String att) {
      if (att.equals("messageid")) {
         return this.messageid;
      } else if (att.equals("datelastchanged")) {
         return this.datelastchanged;
      } else if (att.equals("datehash")) {
         return this.datehash;
      } else if (att.equals("method")) {
         return this.method;
      } else if (att.equals("retired")) {
         return this.retire ? "true" : "false";
      } else {
         return null;
      }
   }

   protected void set(String att, String val) throws NoSuchElementException {
      if (att.equals("messageid")) {
         this.messageid = val;
      } else if (att.equals("method")) {
         this.setMethod(val);
      } else if (att.equals("datelastchanged")) {
         this.datelastchanged = val;
      } else if (att.equals("datehash")) {
         this.datehash = val;
      } else if (att.equals("retired")) {
         this.setRetired(val);
      } else {
         throw new NoSuchElementException(att);
      }
   }

   public void addMessageBody(MessageBody body) {
      this.messageBody = body;
   }

   public MessageBody getMessageBody() {
      return this.messageBody;
   }

   public abstract String toXML(boolean var1);

   public StringBuffer startXML(StringBuffer buff, String label) {
      buff.append("   <" + label + "\n");
      return buff;
   }

   public StringBuffer endXML(StringBuffer buff, String label) {
      buff.append("   </" + label + ">\n");
      return buff;
   }

   public StringBuffer addCommentToXML(StringBuffer buff) {
      if (this.comment != null) {
         buff.append("   <!--  " + this.comment + "-->\n");
      }

      return buff;
   }

   public StringBuffer addAttributeToXML(StringBuffer buff, String attrLabel, String attr) {
      buff.append("      " + attrLabel + "=\"" + attr + "\"\n");
      return buff;
   }

   public StringBuffer addAttributeToXML(StringBuffer buff, String attrLabel, String[] attr) {
      for(int i = 0; i < attr.length; ++i) {
         buff.append("      " + attrLabel + "=\"" + attr[i] + "\"\n");
      }

      return buff;
   }

   public StringBuffer endAttributeToXML(StringBuffer buff) {
      buff.append("      >\n");
      return buff;
   }

   public StringBuffer addSubElementToXML(StringBuffer buff, String subElementLabel, String subElement, boolean fmtCData) {
      StringBuffer b = new StringBuffer();

      for(int i = 0; i < subElement.length(); ++i) {
         char ch = subElement.charAt(i);
         switch (ch) {
            case '"':
               b.append("&quot;");
               break;
            case '&':
               b.append("&amp;");
               break;
            case '\'':
               b.append("&apos;");
               break;
            case '<':
               b.append("&lt;");
               break;
            case '>':
               b.append("&gt;");
               break;
            case ' ':
               b.append("&nbsp;");
               break;
            default:
               b.append(ch);
         }
      }

      String newLine = "";
      String indent1 = "";
      String indent2 = "";
      if (fmtCData) {
         newLine = "\n";
         indent1 = "         ";
         indent2 = "      ";
      }

      buff.append("      <" + subElementLabel + ">" + newLine);
      buff.append(indent1 + b.toString() + newLine);
      buff.append(indent2 + "</" + subElementLabel + ">\n");
      return buff;
   }

   public void setMethod(String val) throws NoSuchElementException {
      this.method = val;
      this.debug("processing method, " + val);
      List argList = new ArrayList();
      List names = new ArrayList();
      StringTokenizer tokens = new StringTokenizer(val, "() ,");
      if (tokens.hasMoreTokens()) {
         this.setUserMethodName(tokens.nextToken());

         while(tokens.hasMoreTokens()) {
            String t = tokens.nextToken();
            argList.add(t);
            t = tokens.nextToken();
            names.add(t);
         }

         this.arguments = new String[argList.size()];
         this.arguments = (String[])((String[])argList.toArray(this.arguments));
         this.argNames = new String[names.size()];
         this.argNames = (String[])((String[])names.toArray(this.argNames));
      }

   }

   protected void setUserMethodName(String name) {
      this.debug("Setting method name, " + name);
      this.methodName = name;
   }

   protected void validateMethod() throws MethodException {
      if (this.arguments != null && this.arguments.length > 10) {
         throw new MethodException("Only 10 arguments (0 thru 9) are manageable in text areas.");
      }
   }

   private void debug(String msg) {
   }
}
