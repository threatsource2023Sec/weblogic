package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class GeneratingInstructions {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private String processorPackage;
   private String processorSuperClass;
   private String processorClass;
   private String publicId;
   private String dtdURL;
   private Map generatingActions = new HashMap();

   public void setProcessorPackage(String val) {
      this.processorPackage = val;
   }

   public String getProcessorPackage() {
      return this.processorPackage;
   }

   public void setProcessorSuperClass(String val) {
      this.processorSuperClass = val;
   }

   public String getProcessorSuperClass() {
      return this.processorSuperClass;
   }

   public void setProcessorClass(String val) {
      this.processorClass = val;
   }

   public String getProcessorClass() {
      return this.processorClass;
   }

   public void setPublicId(String val) {
      this.publicId = val;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public void setDtdURL(String val) {
      this.dtdURL = val;
   }

   public String getDtdURL() {
      return this.dtdURL;
   }

   public void addGeneratingAction(GAction a) {
      this.generatingActions.put(a.getPath(), a);
   }

   public GAction getGeneratingAction(String pathName) {
      return (GAction)this.generatingActions.get(pathName);
   }

   public Collection getAllGeneratingActions() {
      return this.generatingActions.values();
   }

   public Collection validate() {
      List errs = new ArrayList();
      if (isNull(this.processorPackage)) {
         errs.add("Processor Package must be set");
      }

      if (isNull(this.processorClass)) {
         errs.add("Processor Class Name must be set");
      }

      if (!this.checkDuplicateActions()) {
         errs.add("Multiple actions cannot be declared for the same element");
      }

      return errs;
   }

   private boolean checkDuplicateActions() {
      Set paths = new HashSet();
      Collection actions = this.getAllGeneratingActions();
      Iterator ai = actions.iterator();

      GAction a;
      do {
         if (!ai.hasNext()) {
            return true;
         }

         a = (GAction)ai.next();
      } while(paths.add(a.getPath()));

      return false;
   }

   private static boolean isNull(String s) {
      return s == null || s.length() == 0;
   }
}
