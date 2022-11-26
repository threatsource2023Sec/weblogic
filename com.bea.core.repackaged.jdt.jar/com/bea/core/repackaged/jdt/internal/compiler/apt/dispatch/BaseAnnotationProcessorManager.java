package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.AbstractAnnotationProcessorManager;
import com.bea.core.repackaged.jdt.internal.compiler.Compiler;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAnnotationProcessorManager extends AbstractAnnotationProcessorManager implements IProcessorProvider {
   protected PrintWriter _out;
   protected PrintWriter _err;
   protected BaseProcessingEnvImpl _processingEnv;
   public boolean _isFirstRound = true;
   protected List _processors = new ArrayList();
   protected boolean _printProcessorInfo = false;
   protected boolean _printRounds = false;
   protected int _round;

   public void configure(Object batchCompiler, String[] options) {
      throw new UnsupportedOperationException();
   }

   public void configureFromPlatform(Compiler compiler, Object compilationUnitLocator, Object javaProject, boolean isTestCode) {
      throw new UnsupportedOperationException();
   }

   public List getDiscoveredProcessors() {
      return this._processors;
   }

   public ICompilationUnit[] getDeletedUnits() {
      return this._processingEnv.getDeletedUnits();
   }

   public ICompilationUnit[] getNewUnits() {
      return this._processingEnv.getNewUnits();
   }

   public ReferenceBinding[] getNewClassFiles() {
      return this._processingEnv.getNewClassFiles();
   }

   public void reset() {
      this._processingEnv.reset();
   }

   public void setErr(PrintWriter err) {
      this._err = err;
   }

   public void setOut(PrintWriter out) {
      this._out = out;
   }

   public void setProcessors(Object[] processors) {
      throw new UnsupportedOperationException();
   }

   public void processAnnotations(CompilationUnitDeclaration[] units, ReferenceBinding[] referenceBindings, boolean isLastRound) {
      if (units != null) {
         CompilationUnitDeclaration[] var7 = units;
         int var6 = units.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            CompilationUnitDeclaration declaration = var7[var5];
            if (declaration != null && declaration.scope != null) {
               ModuleBinding m = declaration.scope.module();
               if (m != null) {
                  this._processingEnv._current_module = m;
                  break;
               }
            }
         }
      }

      RoundEnvImpl roundEnv = new RoundEnvImpl(units, referenceBindings, isLastRound, this._processingEnv);
      PrintWriter traceProcessorInfo = this._printProcessorInfo ? this._out : null;
      PrintWriter traceRounds = this._printRounds ? this._out : null;
      if (traceRounds != null) {
         traceRounds.println("Round " + ++this._round + ':');
      }

      RoundDispatcher dispatcher = new RoundDispatcher(this, roundEnv, roundEnv.getRootAnnotations(), traceProcessorInfo, traceRounds);
      dispatcher.round();
      if (this._isFirstRound) {
         this._isFirstRound = false;
      }

   }
}
