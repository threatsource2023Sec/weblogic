package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProcessingInstructions {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private String processorPackage;
   private String processorSuperClass;
   private String processorClass;
   private String publicId;
   private String localDTDResourceName;
   private String dtdURL;
   private PAction documentStartAction;
   private PAction documentEndAction;
   private Map processingActions = new HashMap();

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

   public void setLocalDTDResourceName(String val) {
      this.localDTDResourceName = val;
   }

   public String getLocalDTDResourceName() {
      return this.localDTDResourceName;
   }

   public void setDtdURL(String val) {
      this.dtdURL = val;
   }

   public String getDtdURL() {
      return this.dtdURL;
   }

   public void setDocumentStartAction(PAction a) {
      this.documentStartAction = a;
   }

   public PAction getDocumentStartAction() {
      return this.documentStartAction;
   }

   public void setDocumentEndAction(PAction a) {
      this.documentEndAction = a;
   }

   public PAction getDocumentEndAction() {
      return this.documentEndAction;
   }

   public void addProcessingAction(PAction a) throws SAXProcessorException {
      String[] paths = a.getPaths();

      for(int i = 0; i < paths.length; ++i) {
         PAction preva = (PAction)this.processingActions.put(paths[i], a);
         if (preva != null) {
            StringBuffer msg = new StringBuffer();
            msg.append("Cannot define more than one processing action for path=");
            msg.append(paths[i]);
            throw new SAXProcessorException(msg.toString());
         }
      }

   }

   public PAction getProcessingAction(String pathName) {
      return (PAction)this.processingActions.get(pathName);
   }

   public Collection getAllProcessingActions() {
      return this.processingActions.values();
   }

   public Collection validate() {
      List errs = new ArrayList();
      if (isNull(this.processorPackage)) {
         errs.add("Processor Package must be set");
      }

      if (isNull(this.processorClass)) {
         errs.add("Processor Class Name must be set");
      }

      if (isNull(this.publicId)) {
         errs.add("Public Id for input XML files must be set");
      }

      return errs;
   }

   private static boolean isNull(String s) {
      return s == null || s.length() == 0;
   }
}
