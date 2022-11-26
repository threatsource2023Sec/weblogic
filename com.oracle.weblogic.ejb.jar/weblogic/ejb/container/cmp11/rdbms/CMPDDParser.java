package weblogic.ejb.container.cmp11.rdbms;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class CMPDDParser {
   private WeblogicRdbmsJarBean cmpDescriptor;
   private String currentEJBName;
   private String fileName = null;
   protected String encoding = null;
   private CompatibilitySettings compat;
   protected EjbDescriptorBean ejbDescriptor;

   public void setEJBDescriptor(EjbDescriptorBean ejbDescriptor) {
      this.ejbDescriptor = ejbDescriptor;
   }

   public void setFileName(String theFileName) {
      this.fileName = theFileName;
   }

   protected String getFileName() {
      return this.fileName;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setCurrentEJBName(String ejbName) {
      this.currentEJBName = ejbName;
   }

   protected String getCurrentEJBName() {
      return this.currentEJBName;
   }

   public void setDescriptorMBean(WeblogicRdbmsJarBean cmpDesc) {
      this.cmpDescriptor = cmpDesc;
   }

   public WeblogicRdbmsJarBean getDescriptorMBean() {
      return this.cmpDescriptor;
   }

   public CompatibilitySettings getCompatibilitySettings() {
      return this.compat;
   }

   public abstract void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

   public void setUseQuotedNames(boolean useQN) {
      if (this.compat == null) {
         this.compat = new CompatibilitySettings();
      }

      this.compat.useQuotedNames = useQN;
   }

   public void setIsolationLevel(Integer iso) {
      if (this.compat == null) {
         this.compat = new CompatibilitySettings();
      }

      this.compat.isolationLevel = iso;
   }

   protected void addFinderExpression(String signature, FinderExpression fex) {
      if (this.compat == null) {
         this.compat = new CompatibilitySettings();
      }

      List l = (List)this.compat.finderExpressionMap.get(signature);
      if (l == null) {
         l = new ArrayList();
         this.compat.finderExpressionMap.put(signature, l);
      }

      ((List)l).add(fex);
   }

   public static class CompatibilitySettings {
      public boolean useQuotedNames = false;
      public Integer isolationLevel = null;
      public Map finderExpressionMap = new HashMap();
   }

   public static class FinderExpression {
      public int expressionNumber;
      public String expressionText;
      public String expressionType;
   }
}
