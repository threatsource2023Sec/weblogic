package kodo.bea;

import java.io.PrintWriter;
import serp.util.Strings;

class LocalizedMessage {
   private final String packageName;
   private final String shortKey;
   private final String kodoKey;
   private String kodoMessage;
   private boolean hasBEAId = false;
   private int beaId;
   private String beaMessageBody;

   public LocalizedMessage(String packageName, String shortKey, String kodoMessage) {
      if (packageName == null) {
         throw new NullPointerException();
      } else if (shortKey == null) {
         throw new NullPointerException();
      } else {
         this.packageName = packageName;
         this.shortKey = shortKey;
         this.kodoKey = LocalizedMessageMapper.toKodoKey(packageName, shortKey);
         this.kodoMessage = kodoMessage;
      }
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getUnqualifiedKodoKey() {
      return this.shortKey;
   }

   public String getKodoKey() {
      return this.kodoKey;
   }

   public String getKodoMessage() {
      if (this.hasMessageConflict()) {
         throw new IllegalStateException("\"" + this.kodoMessage + "\" != \"" + this.beaMessageBody + "\"");
      } else {
         return this.kodoMessage;
      }
   }

   public void setBEAId(int id) {
      this.hasBEAId = true;
      this.beaId = id;
   }

   public int getBEAId() {
      return this.beaId;
   }

   public boolean hasBEAId() {
      return this.hasBEAId;
   }

   public String getBEASubsystem() {
      return this.packageName.indexOf(".jdbc") != -1 ? "Kodo JDBC" : "Kodo";
   }

   public String getBEAMessageBody() {
      if (this.hasMessageConflict()) {
         throw new IllegalStateException("\"" + this.kodoMessage + "\" != \"" + this.beaMessageBody + "\"");
      } else {
         String body;
         if (this.beaMessageBody != null) {
            body = this.beaMessageBody;
         } else {
            body = this.kodoMessage;
         }

         body = Strings.replace(body, "<", "&lt;");
         body = Strings.replace(body, ">", "&gt;");
         return body;
      }
   }

   public boolean hasMessageConflict() {
      return this.beaMessageBody != null && !this.kodoMessage.equals(this.beaMessageBody);
   }

   public void writeXML(PrintWriter out, String eol) {
      String pad1 = "    ";
      String pad2 = "        ";
      String pad3 = "            ";
      StringBuffer buf = new StringBuffer();
      buf.append(pad1).append("<!-- Kodo key: ").append(this.kodoKey).append(" -->").append(eol);
      buf.append(pad1).append("<logmessage").append(eol);
      buf.append(pad2).append("messageid=\"").append(this.beaId).append("\"").append(eol);
      buf.append(pad2).append("datelastchanged=\"\"").append(eol);
      buf.append(pad2).append("datehash=\"\"").append(eol);
      buf.append(pad2).append("severity=\"dynamic\"").append(eol);
      buf.append(pad2).append("method=\"log").append(this.beaId).append("()\"").append(eol);
      buf.append(pad2).append(">").append(eol);
      buf.append(pad2).append("<messagebody>").append(eol);
      buf.append(pad3).append(this.getBEAMessageBody()).append(eol);
      buf.append(pad2).append("</messagebody>").append(eol);
      buf.append(pad2).append("<messagedetail/>").append(eol);
      buf.append(pad2).append("<cause/>").append(eol);
      buf.append(pad2).append("<action/>").append(eol);
      buf.append(pad1).append("</logmessage>").append(eol);
      buf.append(eol);
      out.print(buf.toString());
   }
}
