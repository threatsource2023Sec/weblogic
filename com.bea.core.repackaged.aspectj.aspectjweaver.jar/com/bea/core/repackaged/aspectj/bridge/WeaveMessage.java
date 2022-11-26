package com.bea.core.repackaged.aspectj.bridge;

public class WeaveMessage extends Message {
   public static WeaveMessageKind WEAVEMESSAGE_DECLAREPARENTSIMPLEMENTS = new WeaveMessageKind(1, "Extending interface set for type '%1' (%2) to include '%3' (%4)");
   public static WeaveMessageKind WEAVEMESSAGE_ITD = new WeaveMessageKind(2, "Type '%1' (%2) has intertyped %3 from '%4' (%5)");
   public static WeaveMessageKind WEAVEMESSAGE_ADVISES = new WeaveMessageKind(3, "Join point '%1' in Type '%2' (%3) advised by %4 advice from '%5' (%6)%7");
   public static WeaveMessageKind WEAVEMESSAGE_DECLAREPARENTSEXTENDS = new WeaveMessageKind(4, "Setting superclass of type '%1' (%2) to '%3' (%4)");
   public static WeaveMessageKind WEAVEMESSAGE_SOFTENS = new WeaveMessageKind(5, "Softening exceptions in type '%1' (%2) as defined by aspect '%3' (%4)");
   public static WeaveMessageKind WEAVEMESSAGE_ANNOTATES = new WeaveMessageKind(6, "'%1' (%2) is annotated with %3 %4 annotation from '%5' (%6)");
   public static WeaveMessageKind WEAVEMESSAGE_MIXIN = new WeaveMessageKind(7, "Mixing interface '%1' (%2) into type '%3' (%4)");
   public static WeaveMessageKind WEAVEMESSAGE_REMOVES_ANNOTATION = new WeaveMessageKind(6, "'%1' (%2) has had %3 %4 annotation removed by '%5' (%6)");
   private String affectedtypename;
   private String aspectname;

   private WeaveMessage(String message, String affectedtypename, String aspectname) {
      super(message, IMessage.WEAVEINFO, (Throwable)null, (ISourceLocation)null);
      this.affectedtypename = affectedtypename;
      this.aspectname = aspectname;
   }

   public static WeaveMessage constructWeavingMessage(WeaveMessageKind kind, String[] inserts) {
      StringBuffer str = new StringBuffer(kind.getMessage());
      int pos = true;

      int pos;
      while((pos = (new String(str)).indexOf("%")) != -1) {
         int n = Character.getNumericValue(str.charAt(pos + 1));
         str.replace(pos, pos + 2, inserts[n - 1]);
      }

      return new WeaveMessage(str.toString(), (String)null, (String)null);
   }

   public static WeaveMessage constructWeavingMessage(WeaveMessageKind kind, String[] inserts, String affectedtypename, String aspectname) {
      StringBuffer str = new StringBuffer(kind.getMessage());
      int pos = true;

      int pos;
      while((pos = (new String(str)).indexOf("%")) != -1) {
         int n = Character.getNumericValue(str.charAt(pos + 1));
         str.replace(pos, pos + 2, inserts[n - 1]);
      }

      return new WeaveMessage(str.toString(), affectedtypename, aspectname);
   }

   public String getAspectname() {
      return this.aspectname;
   }

   public String getAffectedtypename() {
      return this.affectedtypename;
   }

   public static class WeaveMessageKind {
      private String message;

      public WeaveMessageKind(int id, String message) {
         this.message = message;
      }

      public String getMessage() {
         return this.message;
      }
   }
}
