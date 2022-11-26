package weblogic.xml.process;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class GeneratingContext extends Node {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private Writer out;
   private boolean haveWrittenStartTag = false;
   private boolean haveWrittenNode = false;
   private int nchildren = 0;
   private boolean delayedWrite = false;

   public GeneratingContext(String tagName) throws XMLProcessingException {
      super(tagName);
   }

   public GeneratingContext(GeneratingContext parent, String tagName) throws XMLProcessingException {
      super(parent, tagName);
      ++parent.nchildren;
   }

   public Writer getWriter() {
      return this.out;
   }

   public void setWriter(Writer w) {
      this.out = w;
   }

   public GeneratingContext newElementNode(String tagName) throws XMLProcessingException {
      GeneratingContext newCtx = new GeneratingContext(this, tagName);
      newCtx.delayedWrite = this.delayedWrite;
      newCtx.out = this.out;
      if (!this.delayedWrite && !this.haveWrittenStartTag) {
         try {
            this.writeStartTag();
         } catch (IOException var4) {
            throw new XMLProcessingException(var4);
         }
      }

      return newCtx;
   }

   public GeneratingContext newTextNode() throws XMLProcessingException {
      return this.newElementNode("#text");
   }

   public void setDelayedWrite(boolean val) {
      this.delayedWrite = val;
   }

   public boolean delayedWrite() {
      return this.delayedWrite;
   }

   public Node release() throws XMLProcessingException {
      try {
         if (this.delayedWrite) {
            if (this.parent != null && ((GeneratingContext)this.parent).delayedWrite) {
               return this.parent;
            } else {
               this.writeNode();
               return super.release();
            }
         } else {
            if (this.isText()) {
               this.writeValue();
            } else {
               if (!this.haveWrittenStartTag) {
                  this.writeStartTag();
               }

               this.writeEndTag();
            }

            this.haveWrittenNode = true;
            return super.release();
         }
      } catch (IOException var2) {
         throw new XMLProcessingException(var2);
      }
   }

   public void writeNode() throws IOException {
      if (!this.haveWrittenNode) {
         if (this.isText()) {
            this.writeValue();
         } else {
            this.writeStartTag();
            this.writeSubNodes();
            this.writeEndTag();
         }

         this.haveWrittenNode = true;
      }
   }

   private void writeSubNodes() throws IOException {
      Iterator i = this.children.iterator();

      while(i.hasNext()) {
         GeneratingContext ctx = (GeneratingContext)i.next();
         ctx.writeNode();
      }

   }

   private void writeStartTag() throws IOException {
      this.out.write("\n" + indent(this.level) + "<" + this.name);
      this.writeAttributes();
      this.out.write(">");
      this.haveWrittenStartTag = true;
   }

   private void writeAttributes() throws IOException {
      Collection attrEntries = this.attributes.entrySet();
      if (attrEntries.size() > 0) {
         this.out.write(" ");
      }

      Iterator i = attrEntries.iterator();

      while(i.hasNext()) {
         Map.Entry ent = (Map.Entry)i.next();
         String attrName = (String)ent.getKey();
         String attrVal = (String)ent.getValue();
         this.out.write(attrName + "=\"" + attrVal + "\" ");
      }

   }

   private void writeValue() throws IOException {
      this.out.write("\n" + indent(this.level) + this.getValue());
   }

   private void writeEndTag() throws IOException {
      this.out.write("\n" + indent(this.level) + "</" + this.name + ">");
   }

   private static String indent(int nspaces) {
      StringBuffer sbuf = new StringBuffer();

      for(int i = 0; i < nspaces; ++i) {
         sbuf.append("  ");
      }

      return sbuf.toString();
   }
}
