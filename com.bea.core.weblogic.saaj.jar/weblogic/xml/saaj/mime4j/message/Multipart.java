package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Multipart implements Body {
   private String preamble = "";
   private String epilogue = "";
   private List bodyParts = new LinkedList();
   private Entity parent = null;
   private String subType = "alternative";

   public String getSubType() {
      return this.subType;
   }

   public void setSubType(String subType) {
      this.subType = subType;
   }

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity parent) {
      this.parent = parent;
      Iterator it = this.bodyParts.iterator();

      while(it.hasNext()) {
         ((BodyPart)it.next()).setParent(parent);
      }

   }

   public String getEpilogue() {
      return this.epilogue;
   }

   public void setEpilogue(String epilogue) {
      this.epilogue = epilogue;
   }

   public List getBodyParts() {
      return Collections.unmodifiableList(this.bodyParts);
   }

   public void setBodyParts(List bodyParts) {
      this.bodyParts = bodyParts;
      Iterator it = bodyParts.iterator();

      while(it.hasNext()) {
         ((BodyPart)it.next()).setParent(this.parent);
      }

   }

   public void addBodyPart(BodyPart bodyPart) {
      this.bodyParts.add(bodyPart);
      bodyPart.setParent(this.parent);
   }

   public String getPreamble() {
      return this.preamble;
   }

   public void setPreamble(String preamble) {
      this.preamble = preamble;
   }

   public void writeTo(OutputStream out) throws IOException {
   }
}
