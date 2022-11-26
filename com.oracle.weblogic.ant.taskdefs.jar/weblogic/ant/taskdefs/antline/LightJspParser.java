package weblogic.ant.taskdefs.antline;

import java.util.ArrayList;
import java.util.Iterator;

/** @deprecated */
@Deprecated
public class LightJspParser {
   private String page;
   private ResourceProvider provider;

   public LightJspParser(String page, ResourceProvider provider) {
      this.page = page;
      this.provider = provider;
   }

   public String parse() throws ScriptException {
      Iterator tags = this.getTags();
      StringBuffer sb = new StringBuffer();

      while(tags.hasNext()) {
         Tag tag = (Tag)tags.next();
         sb.append(tag.getJavaScript());
      }

      return sb.toString();
   }

   private Iterator getTags() throws ScriptException {
      ArrayList tags = new ArrayList();
      Iterator lines = this.split(this.page);

      while(lines.hasNext()) {
         String line = (String)lines.next();
         Tag tag = this.createTag(line);
         tag.setContent(line);
         tags.add(tag);
      }

      return tags.iterator();
   }

   private Iterator split(String page) throws ScriptException {
      String start = "<%";
      String end = "%>";
      ArrayList lines = new ArrayList();
      int pos = 0;
      boolean done = false;

      while(!done) {
         int startIndex = page.indexOf(start, pos);
         if (startIndex == -1) {
            lines.add(page.substring(pos, page.length()));
            done = true;
         } else {
            if (pos != startIndex) {
               lines.add(page.substring(pos, startIndex));
            }

            int endIndex = page.indexOf(end, startIndex);
            if (endIndex == -1) {
               throw new ScriptException("unable to find the end tag " + startIndex + " " + (lines.size() == 0 ? "at start" : lines.get(lines.size() - 1)));
            }

            lines.add(page.substring(startIndex, endIndex + 2));
            pos = endIndex + 2;
         }
      }

      return lines.iterator();
   }

   private Tag createTag(String line) {
      if (line.startsWith("<%--")) {
         return new Comment();
      } else if (line.startsWith("<%=")) {
         return new Expression();
      } else if (line.startsWith("<%!")) {
         return new Declaration();
      } else if (line.startsWith("<%@")) {
         return new Include(this.provider);
      } else {
         return (Tag)(line.startsWith("<%") ? new Scriptlet() : new Text());
      }
   }
}
