package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class SessionAcallDescriptor implements CallDescriptor, Serializable {
   private static final long serialVersionUID = -302823085137525219L;
   private int myCd;
   private boolean isConversational;
   private boolean hasCallBack = false;

   public SessionAcallDescriptor(int cd, boolean conversational) {
      this.myCd = cd;
      this.isConversational = conversational;
   }

   public int getCd() {
      return this.myCd;
   }

   public void setHasCallback(boolean hasCallBack) {
      this.hasCallBack = hasCallBack;
   }

   public boolean hasCallBack() {
      return this.hasCallBack;
   }

   public boolean isConversational() {
      return this.isConversational;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof SessionAcallDescriptor)) {
         return false;
      } else {
         SessionAcallDescriptor toCompare = (SessionAcallDescriptor)obj;
         if (this.myCd != toCompare.getCd()) {
            return false;
         } else {
            return this.isConversational == toCompare.isConversational();
         }
      }
   }

   public int hashCode() {
      return this.myCd & '\uffff';
   }

   public String toString() {
      return "SessionAcallDescriptor:" + this.myCd + ":" + this.isConversational;
   }
}
