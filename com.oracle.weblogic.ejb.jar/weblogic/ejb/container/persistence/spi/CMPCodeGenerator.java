package weblogic.ejb.container.persistence.spi;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.ejbc.EjbCodeGenerator;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.utils.AssertionError;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerator;

public abstract class CMPCodeGenerator extends EjbCodeGenerator {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   protected CMPBeanDescriptor bd = null;
   private PersistenceType associatedType;
   protected Hashtable finderMethods;

   public CMPCodeGenerator() {
   }

   public CMPCodeGenerator(Getopt2 opts) {
      super(opts);
   }

   public void setAssociatedType(PersistenceType type) {
      this.associatedType = type;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor cmebd) {
      this.bd = cmebd;
   }

   protected void addOutputs(Vector outputs, BeanInfo bi, NamingConvention nc) throws EJBCException {
      if (!(bi instanceof EntityBeanInfo)) {
         throw new AssertionError("Can only generate container managed persistence code for EntityBeans.");
      } else {
         EntityBeanInfo ebi = (EntityBeanInfo)bi;
         Output beanImpl;
         if (ebi.hasRemoteClientView() || ebi.hasLocalClientView()) {
            beanImpl = new Output(this);
            beanImpl.setBeanInfo(bi);
            beanImpl.setNamingConvention(nc);
            beanImpl.setPackage(nc.getBeanPackageName());
            beanImpl.setCMPBeanDescriptor(this.bd);
            beanImpl.setPersistenceType(this.associatedType);
            List templateNames = this.typeSpecificTemplates();
            Iterator iter = templateNames.iterator();
            boolean first = true;

            while(iter.hasNext()) {
               String templateName = (String)iter.next();
               if (first) {
                  beanImpl.setTemplate(templateName);
                  first = false;
               } else {
                  beanImpl.addExtraTemplate(templateName);
               }
            }

            beanImpl.setOutputFile(nc.getSimpleCmpBeanClassName(this.ejbStoreType()) + ".java");
            outputs.addElement(beanImpl);
         }

         beanImpl = new Output(this);
         beanImpl.setBeanInfo(bi);
         beanImpl.setNamingConvention(nc);
         beanImpl.setPackage(nc.getBeanPackageName());
         beanImpl.setCMPBeanDescriptor(this.bd);
         beanImpl.setPersistenceType(this.associatedType);
         beanImpl.setTemplate("weblogic/ejb/container/ejbc/ejbBeanIntf.j");
         beanImpl.setOutputFile(nc.getSimpleGeneratedBeanInterfaceName() + ".java");
         outputs.addElement(beanImpl);
      }
   }

   protected void prepare(CodeGenerator.Output output) throws EJBCException, ClassNotFoundException {
      super.prepare(output);
      this.finderMethods = new Hashtable();
      Method[] fmethods = null;
      if (this.hasLocalClientView) {
         fmethods = this.localFindMethods;
      } else {
         fmethods = this.findMethods;
      }

      if (fmethods != null) {
         Method[] var3 = fmethods;
         int var4 = fmethods.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            MethodSignature ms = new MethodSignature(m);
            ms.setAbstract(false);
            this.finderMethods.put(ms.asNameAndParamTypesKey(), ms);
         }
      }

   }

   private String ejbStoreType() {
      return this.associatedType.getIdentifier();
   }

   protected abstract List typeSpecificTemplates();

   public List listGenerate() throws Exception {
      String[] stringArray = this.generate();
      ArrayList stringList = new ArrayList(stringArray.length);

      for(int i = 0; i < stringArray.length; ++i) {
         stringList.set(i, stringArray[i]);
      }

      return stringList;
   }

   public String cmpBeanClassName() {
      return this.nc.getSimpleCmpBeanClassName(this.ejbStoreType());
   }

   public static void addToHashtable(Hashtable source, Hashtable dest) {
      if (source != null) {
         Enumeration e = source.keys();
         if (e != null) {
            while(e.hasMoreElements()) {
               Object key = e.nextElement();
               dest.put(key, source.get(key));
            }

         }
      }
   }

   public static class Output extends EjbCodeGenerator.Output {
      private PersistenceType associatedType = null;
      private CMPCodeGenerator owningGenerator = null;
      private CMPBeanDescriptor beanDesc = null;

      public Output(CMPCodeGenerator generator) {
         this.owningGenerator = generator;
      }

      public InputStream getTemplateStream(String templateName) throws IOException {
         return this.getClass().getResourceAsStream("/" + templateName);
      }

      public void setPersistenceType(PersistenceType persistenceType) {
         this.associatedType = persistenceType;
      }

      public CMPBeanDescriptor getCMPBeanDescriptor() {
         return this.beanDesc;
      }

      public void setCMPBeanDescriptor(CMPBeanDescriptor desc) {
         this.beanDesc = desc;
      }
   }
}
