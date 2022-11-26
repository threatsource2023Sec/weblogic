package com.sun.faces.flow;

import java.io.Serializable;
import javax.faces.flow.ViewNode;

public class ViewNodeImpl extends ViewNode implements Serializable {
   private static final long serialVersionUID = -7577859001307479164L;
   private final String id;
   private final String vdlDocumentId;

   public ViewNodeImpl(String id, String vdlDocumentIdIn) {
      this.id = id;
      int i = vdlDocumentIdIn.indexOf("META-INF/flows");
      if (-1 != i) {
         vdlDocumentIdIn = vdlDocumentIdIn.substring(i + 14);
      } else if (vdlDocumentIdIn.startsWith("/WEB-INF")) {
         vdlDocumentIdIn = vdlDocumentIdIn.substring(8);
      } else if (vdlDocumentIdIn.startsWith("WEB-INF")) {
         vdlDocumentIdIn = vdlDocumentIdIn.substring(7);
      }

      this.vdlDocumentId = vdlDocumentIdIn;
   }

   public String getId() {
      return this.id;
   }

   public String getVdlDocumentId() {
      return this.vdlDocumentId;
   }
}
