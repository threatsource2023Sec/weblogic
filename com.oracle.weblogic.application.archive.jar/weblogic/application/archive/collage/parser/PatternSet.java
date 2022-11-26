package weblogic.application.archive.collage.parser;

import java.util.Iterator;
import java.util.LinkedList;
import org.xml.sax.Attributes;

public class PatternSet extends Container {
   String id;
   Collage collage;
   LinkedList patterns = new LinkedList();
   private static String newLine = System.getProperty("line.separator");

   public PatternSet(Collage parentCollage, Attributes attributes) {
      this.collage = parentCollage;
      this.id = attributes.getValue("id");
   }

   protected void add(Object o) {
      if (o instanceof PatternSet) {
         this.patterns.addAll(((PatternSet)o).patterns);
      } else if (o instanceof Pattern) {
         this.patterns.add((Pattern)o);
      }

   }

   public void addPatternWithId(String id) {
      this.add(this.collage.getPatternSetWithId(id));
   }

   public StringBuffer toString(String indent, StringBuffer sb) {
      Iterator var3 = this.patterns.iterator();

      while(var3.hasNext()) {
         Pattern p = (Pattern)var3.next();
         sb.append(indent).append(p.toString()).append(newLine);
      }

      return sb;
   }
}
