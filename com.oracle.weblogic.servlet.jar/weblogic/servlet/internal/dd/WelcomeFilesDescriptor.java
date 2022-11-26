package weblogic.servlet.internal.dd;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.WelcomeFileListMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WelcomeFilesDescriptor extends BaseServletDescriptor implements ToXML, WelcomeFileListMBean {
   private static final long serialVersionUID = 8641270140745843773L;
   private static final String WELCOME_FILE = "welcome-file";
   private String[] fileNames;

   public WelcomeFilesDescriptor() {
      this.fileNames = new String[0];
   }

   public WelcomeFilesDescriptor(WelcomeFileListMBean mbean) {
      if (mbean != null) {
         String[] files = mbean.getWelcomeFiles();
         String[] result = new String[files.length];
         System.arraycopy(files, 0, result, 0, files.length);
         this.fileNames = result;
      } else {
         this.fileNames = new String[0];
      }

   }

   public WelcomeFilesDescriptor(Element parentElement) throws DOMProcessingException {
      List wfList = DOMUtils.getValuesByTagName(parentElement, "welcome-file");
      if (wfList == null) {
         this.fileNames = new String[0];
      } else {
         String[] result = new String[wfList.size()];
         Iterator i = wfList.iterator();

         for(int idx = 0; i.hasNext(); ++idx) {
            result[idx] = (String)i.next();
         }

         this.fileNames = result;
      }

   }

   public String[] getWelcomeFiles() {
      return this.fileNames;
   }

   public void setWelcomeFiles(String[] x) {
      String[] old = this.fileNames;
      if (x == null) {
         this.fileNames = new String[0];
      } else {
         this.fileNames = x;
      }

      if (!comp(old, x)) {
         this.firePropertyChange("welcomeFiles", old, x);
      }

   }

   public void addWelcomeFile(String x) {
      String[] prev = this.getWelcomeFiles();
      String[] curr = new String[prev.length + 1];
      System.arraycopy(prev, 0, curr, 0, prev.length);
      curr[prev.length] = x;
      this.setWelcomeFiles(curr);
   }

   public void removeWelcomeFile(String x) {
      String[] prev = this.getWelcomeFiles();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            String[] curr = new String[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setWelcomeFiles(curr);
         }

      }
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      String[] files = this.getWelcomeFiles();
      boolean ok = true;

      for(int i = 0; i < files.length; ++i) {
         if (files[i] == null || files[i].length() == 0) {
            this.addDescriptorError("INVALID_WELCOME_FILE", "" + i);
            ok = false;
         }
      }

      if (!ok) {
         throw new DescriptorValidationException("INVALID_WELCOME_FILE");
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      if (this.fileNames != null && this.fileNames.length != 0) {
         result = result + this.indentStr(indent) + "<welcome-file-list>\n";
         indent += 2;

         for(int i = 0; i < this.fileNames.length; ++i) {
            String f = this.fileNames[i];
            result = result + this.indentStr(indent) + "<welcome-file>" + f + "</welcome-file>\n";
         }

         indent -= 2;
         result = result + this.indentStr(indent) + "</welcome-file-list>\n";
         return result;
      } else {
         return result;
      }
   }
}
