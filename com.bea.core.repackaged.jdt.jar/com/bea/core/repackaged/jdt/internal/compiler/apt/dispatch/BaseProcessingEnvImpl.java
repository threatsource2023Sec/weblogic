package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.Compiler;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.ElementsImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.Factory;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.TypesImpl;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class BaseProcessingEnvImpl implements ProcessingEnvironment {
   protected Filer _filer;
   protected Messager _messager;
   protected Map _processorOptions;
   protected Compiler _compiler;
   protected Elements _elementUtils = ElementsImpl.create(this);
   protected Types _typeUtils = new TypesImpl(this);
   private List _addedUnits = new ArrayList();
   private List _addedClassFiles = new ArrayList();
   private List _deletedUnits = new ArrayList();
   private boolean _errorRaised = false;
   private Factory _factory = new Factory(this);
   public ModuleBinding _current_module;

   public void addNewUnit(ICompilationUnit unit) {
      this._addedUnits.add(unit);
   }

   public void addNewClassFile(ReferenceBinding binding) {
      this._addedClassFiles.add(binding);
   }

   public Compiler getCompiler() {
      return this._compiler;
   }

   public ICompilationUnit[] getDeletedUnits() {
      ICompilationUnit[] result = new ICompilationUnit[this._deletedUnits.size()];
      this._deletedUnits.toArray(result);
      return result;
   }

   public ICompilationUnit[] getNewUnits() {
      ICompilationUnit[] result = new ICompilationUnit[this._addedUnits.size()];
      this._addedUnits.toArray(result);
      return result;
   }

   public Elements getElementUtils() {
      return this._elementUtils;
   }

   public Filer getFiler() {
      return this._filer;
   }

   public Messager getMessager() {
      return this._messager;
   }

   public Map getOptions() {
      return this._processorOptions;
   }

   public Types getTypeUtils() {
      return this._typeUtils;
   }

   public LookupEnvironment getLookupEnvironment() {
      return this._compiler.lookupEnvironment;
   }

   public SourceVersion getSourceVersion() {
      if (this._compiler.options.sourceLevel <= 3211264L) {
         return SourceVersion.RELEASE_5;
      } else if (this._compiler.options.sourceLevel == 3276800L) {
         return SourceVersion.RELEASE_6;
      } else {
         try {
            return SourceVersion.valueOf("RELEASE_7");
         } catch (IllegalArgumentException var1) {
            return SourceVersion.RELEASE_6;
         }
      }
   }

   public void reset() {
      this._addedUnits.clear();
      this._addedClassFiles.clear();
      this._deletedUnits.clear();
   }

   public boolean errorRaised() {
      return this._errorRaised;
   }

   public void setErrorRaised(boolean b) {
      this._errorRaised = true;
   }

   public Factory getFactory() {
      return this._factory;
   }

   public ReferenceBinding[] getNewClassFiles() {
      ReferenceBinding[] result = new ReferenceBinding[this._addedClassFiles.size()];
      this._addedClassFiles.toArray(result);
      return result;
   }
}
