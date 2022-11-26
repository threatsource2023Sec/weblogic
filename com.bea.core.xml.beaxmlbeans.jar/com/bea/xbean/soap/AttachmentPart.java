package com.bea.xbean.soap;

import java.util.Iterator;

public abstract class AttachmentPart {
   public abstract int getSize() throws SOAPException;

   public abstract void clearContent();

   public abstract Object getContent() throws SOAPException;

   public abstract void setContent(Object var1, String var2);

   public String getContentId() {
      String[] as = this.getMimeHeader("Content-Id");
      return as != null && as.length > 0 ? as[0] : null;
   }

   public String getContentLocation() {
      String[] as = this.getMimeHeader("Content-Location");
      return as != null && as.length > 0 ? as[0] : null;
   }

   public String getContentType() {
      String[] as = this.getMimeHeader("Content-Type");
      return as != null && as.length > 0 ? as[0] : null;
   }

   public void setContentId(String contentId) {
      this.setMimeHeader("Content-Id", contentId);
   }

   public void setContentLocation(String contentLocation) {
      this.setMimeHeader("Content-Location", contentLocation);
   }

   public void setContentType(String contentType) {
      this.setMimeHeader("Content-Type", contentType);
   }

   public abstract void removeMimeHeader(String var1);

   public abstract void removeAllMimeHeaders();

   public abstract String[] getMimeHeader(String var1);

   public abstract void setMimeHeader(String var1, String var2);

   public abstract void addMimeHeader(String var1, String var2);

   public abstract Iterator getAllMimeHeaders();

   public abstract Iterator getMatchingMimeHeaders(String[] var1);

   public abstract Iterator getNonMatchingMimeHeaders(String[] var1);
}
