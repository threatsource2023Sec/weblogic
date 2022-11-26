package com.bea.core.repackaged.springframework.beans.support;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistrar;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistrySupport;
import com.bea.core.repackaged.springframework.beans.propertyeditors.ClassArrayEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.ClassEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.FileEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.InputSourceEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.InputStreamEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.PathEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.ReaderEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.URIEditor;
import com.bea.core.repackaged.springframework.beans.propertyeditors.URLEditor;
import com.bea.core.repackaged.springframework.core.env.PropertyResolver;
import com.bea.core.repackaged.springframework.core.io.ContextResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceEditor;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.io.support.ResourceArrayPropertyEditor;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternResolver;
import java.beans.PropertyEditor;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import org.xml.sax.InputSource;

public class ResourceEditorRegistrar implements PropertyEditorRegistrar {
   private final PropertyResolver propertyResolver;
   private final ResourceLoader resourceLoader;

   public ResourceEditorRegistrar(ResourceLoader resourceLoader, PropertyResolver propertyResolver) {
      this.resourceLoader = resourceLoader;
      this.propertyResolver = propertyResolver;
   }

   public void registerCustomEditors(PropertyEditorRegistry registry) {
      ResourceEditor baseEditor = new ResourceEditor(this.resourceLoader, this.propertyResolver);
      this.doRegisterEditor(registry, Resource.class, baseEditor);
      this.doRegisterEditor(registry, ContextResource.class, baseEditor);
      this.doRegisterEditor(registry, InputStream.class, new InputStreamEditor(baseEditor));
      this.doRegisterEditor(registry, InputSource.class, new InputSourceEditor(baseEditor));
      this.doRegisterEditor(registry, File.class, new FileEditor(baseEditor));
      this.doRegisterEditor(registry, Path.class, new PathEditor(baseEditor));
      this.doRegisterEditor(registry, Reader.class, new ReaderEditor(baseEditor));
      this.doRegisterEditor(registry, URL.class, new URLEditor(baseEditor));
      ClassLoader classLoader = this.resourceLoader.getClassLoader();
      this.doRegisterEditor(registry, URI.class, new URIEditor(classLoader));
      this.doRegisterEditor(registry, Class.class, new ClassEditor(classLoader));
      this.doRegisterEditor(registry, Class[].class, new ClassArrayEditor(classLoader));
      if (this.resourceLoader instanceof ResourcePatternResolver) {
         this.doRegisterEditor(registry, Resource[].class, new ResourceArrayPropertyEditor((ResourcePatternResolver)this.resourceLoader, this.propertyResolver));
      }

   }

   private void doRegisterEditor(PropertyEditorRegistry registry, Class requiredType, PropertyEditor editor) {
      if (registry instanceof PropertyEditorRegistrySupport) {
         ((PropertyEditorRegistrySupport)registry).overrideDefaultEditor(requiredType, editor);
      } else {
         registry.registerCustomEditor(requiredType, editor);
      }

   }
}
