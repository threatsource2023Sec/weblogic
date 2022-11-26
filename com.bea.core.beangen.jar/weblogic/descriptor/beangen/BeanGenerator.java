package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.codegen.BasicCodeGeneratorContext;
import weblogic.descriptor.codegen.CodeGenerator;

public class BeanGenerator extends CodeGenerator {
   protected static final String TEMPLATE_DEFAULT = "weblogic/descriptor/beangen/DescriptorBean.template";
   private Map beanMap = new HashMap();

   public BeanGenerator(BeanGenOptions opts) {
      super(opts);
      if (opts != null && opts.isVerbose()) {
         System.out.println(opts.toString());
      }

   }

   public void generate() throws Exception {
      MethodFactory.SINGLETON.registerImplementation(MethodImplementations.FACTORY);
      if (((BeanGenOptions)this.opts).getBaseClassName() == null && ((BeanGenOptions)this.opts).getBaseInterfaceName() == null) {
         throw new BeanGenerationException("Must specify either -baseclass or baseinterface");
      } else {
         this.opts.setDefaultTemplate("weblogic/descriptor/beangen/DescriptorBean.template");
         super.generate();
      }
   }

   public BasicCodeGeneratorContext getCodeGeneratorContext(JClass jClass) {
      return new BasicCodeGeneratorContext(jClass, this) {
         public Object internalGet(String key) {
            if (key.equals("class")) {
               BeanClass bc = (BeanClass)BeanGenerator.this.beanMap.get(this.jClass);
               if (bc == null) {
                  Context.initialize(this.jClass, (BeanGenOptions)BeanGenerator.this.opts);
                  bc = new BeanClass(this.jClass);
                  BeanGenerator.this.beanMap.put(this.jClass, bc);
               }

               return bc;
            } else {
               return key.equals("options") ? Context.get() : super.internalGet(key);
            }
         }
      };
   }

   public Iterator untestedBeans() {
      List untestedBeans = new ArrayList();
      if (this.opts != null && this.opts.getTemplateName() != null && this.opts.getTemplateName().equals("weblogic/descriptor/beangen/DescriptorBean.template")) {
         Iterator beanClasses = this.beanMap.keySet().iterator();

         while(beanClasses.hasNext()) {
            JClass ifcName = (JClass)beanClasses.next();
            BeanClass bc = (BeanClass)this.beanMap.get(ifcName);
            if (bc.needsPostGenValidation()) {
               untestedBeans.add(Context.get().interfaceToClassName(ifcName, false));
            }
         }
      }

      return untestedBeans.iterator();
   }
}
