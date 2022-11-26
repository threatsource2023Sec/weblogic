package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.context.weaving.AspectJWeavingEnabler;
import com.bea.core.repackaged.springframework.context.weaving.DefaultContextLoadTimeWeaver;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

@Configuration
public class LoadTimeWeavingConfiguration implements ImportAware, BeanClassLoaderAware {
   @Nullable
   private AnnotationAttributes enableLTW;
   @Nullable
   private LoadTimeWeavingConfigurer ltwConfigurer;
   @Nullable
   private ClassLoader beanClassLoader;

   public void setImportMetadata(AnnotationMetadata importMetadata) {
      this.enableLTW = AnnotationConfigUtils.attributesFor(importMetadata, (Class)EnableLoadTimeWeaving.class);
      if (this.enableLTW == null) {
         throw new IllegalArgumentException("@EnableLoadTimeWeaving is not present on importing class " + importMetadata.getClassName());
      }
   }

   @Autowired(
      required = false
   )
   public void setLoadTimeWeavingConfigurer(LoadTimeWeavingConfigurer ltwConfigurer) {
      this.ltwConfigurer = ltwConfigurer;
   }

   public void setBeanClassLoader(ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   @Bean(
      name = {"loadTimeWeaver"}
   )
   @Role(2)
   public LoadTimeWeaver loadTimeWeaver() {
      Assert.state(this.beanClassLoader != null, "No ClassLoader set");
      LoadTimeWeaver loadTimeWeaver = null;
      if (this.ltwConfigurer != null) {
         loadTimeWeaver = this.ltwConfigurer.getLoadTimeWeaver();
      }

      if (loadTimeWeaver == null) {
         loadTimeWeaver = new DefaultContextLoadTimeWeaver(this.beanClassLoader);
      }

      if (this.enableLTW != null) {
         EnableLoadTimeWeaving.AspectJWeaving aspectJWeaving = (EnableLoadTimeWeaving.AspectJWeaving)this.enableLTW.getEnum("aspectjWeaving");
         switch (aspectJWeaving) {
            case DISABLED:
            default:
               break;
            case AUTODETECT:
               if (this.beanClassLoader.getResource("META-INF/aop.xml") != null) {
                  AspectJWeavingEnabler.enableAspectJWeaving((LoadTimeWeaver)loadTimeWeaver, this.beanClassLoader);
               }
               break;
            case ENABLED:
               AspectJWeavingEnabler.enableAspectJWeaving((LoadTimeWeaver)loadTimeWeaver, this.beanClassLoader);
         }
      }

      return (LoadTimeWeaver)loadTimeWeaver;
   }
}
