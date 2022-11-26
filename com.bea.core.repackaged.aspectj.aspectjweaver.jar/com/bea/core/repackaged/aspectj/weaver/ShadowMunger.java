package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.util.PartialOrder;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ShadowMunger implements PartialOrder.PartialComparable, IHasPosition {
   public static final ShadowMunger[] NONE = new ShadowMunger[0];
   private static int VERSION_1 = 1;
   protected static final int ShadowMungerAdvice = 1;
   protected static final int ShadowMungerDeow = 2;
   public String handle = null;
   private int shadowMungerKind;
   protected int start;
   protected int end;
   protected ISourceContext sourceContext;
   private ISourceLocation sourceLocation;
   private ISourceLocation binarySourceLocation;
   private File binaryFile;
   private ResolvedType declaringType;
   private boolean isBinary;
   private boolean checkedIsBinary;
   protected Pointcut pointcut;

   protected ShadowMunger() {
   }

   public ShadowMunger(Pointcut pointcut, int start, int end, ISourceContext sourceContext, int shadowMungerKind) {
      this.shadowMungerKind = shadowMungerKind;
      this.pointcut = pointcut;
      this.start = start;
      this.end = end;
      this.sourceContext = sourceContext;
   }

   public boolean match(Shadow shadow, World world) {
      if (world.isXmlConfigured() && world.isAspectIncluded(this.declaringType)) {
         TypePattern scoped = world.getAspectScope(this.declaringType);
         if (scoped != null) {
            Set excludedTypes = (Set)world.getExclusionMap().get(this.declaringType);
            ResolvedType type = shadow.getEnclosingType().resolve(world);
            if (excludedTypes != null && excludedTypes.contains(type)) {
               return false;
            }

            boolean b = scoped.matches(type, TypePattern.STATIC).alwaysTrue();
            if (!b) {
               if (!world.getMessageHandler().isIgnoring(IMessage.INFO)) {
                  world.getMessageHandler().handleMessage(MessageUtil.info("Type '" + type.getName() + "' not woven by aspect '" + this.declaringType.getName() + "' due to scope exclusion in XML definition"));
               }

               if (excludedTypes == null) {
                  Set excludedTypes = new HashSet();
                  excludedTypes.add(type);
                  world.getExclusionMap().put(this.declaringType, excludedTypes);
               } else {
                  excludedTypes.add(type);
               }

               return false;
            }
         }
      }

      if (world.areInfoMessagesEnabled() && world.isTimingEnabled()) {
         long starttime = System.nanoTime();
         FuzzyBoolean isMatch = this.pointcut.match(shadow);
         long endtime = System.nanoTime();
         world.record(this.pointcut, endtime - starttime);
         return isMatch.maybeTrue();
      } else {
         FuzzyBoolean isMatch = this.pointcut.match(shadow);
         return isMatch.maybeTrue();
      }
   }

   public int fallbackCompareTo(Object other) {
      return this.toString().compareTo(this.toString());
   }

   public int getEnd() {
      return this.end;
   }

   public int getStart() {
      return this.start;
   }

   public ISourceLocation getSourceLocation() {
      if (this.sourceLocation == null && this.sourceContext != null) {
         this.sourceLocation = this.sourceContext.makeSourceLocation(this);
      }

      if (this.isBinary()) {
         if (this.binarySourceLocation == null) {
            this.binarySourceLocation = this.getBinarySourceLocation(this.sourceLocation);
         }

         return this.binarySourceLocation;
      } else {
         return this.sourceLocation;
      }
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public void setPointcut(Pointcut pointcut) {
      this.pointcut = pointcut;
   }

   public void setDeclaringType(ResolvedType aType) {
      this.declaringType = aType;
   }

   public ResolvedType getDeclaringType() {
      return this.declaringType;
   }

   public abstract ResolvedType getConcreteAspect();

   public ISourceLocation getBinarySourceLocation(ISourceLocation sl) {
      if (sl == null) {
         return null;
      } else {
         String sourceFileName = null;
         if (this.getDeclaringType() instanceof ReferenceType) {
            String s = ((ReferenceType)this.getDeclaringType()).getDelegate().getSourcefilename();
            int i = s.lastIndexOf(47);
            if (i != -1) {
               sourceFileName = s.substring(i + 1);
            } else {
               sourceFileName = s;
            }
         }

         ISourceLocation sLoc = new SourceLocation(this.getBinaryFile(), sl.getLine(), sl.getEndLine(), sl.getColumn() == 0 ? -2147483647 : sl.getColumn(), sl.getContext(), sourceFileName);
         return sLoc;
      }
   }

   private File getBinaryFile() {
      if (this.binaryFile == null) {
         String binaryPath = this.getDeclaringType().getBinaryPath();
         if (binaryPath == null) {
            binaryPath = "classpath";
            this.getDeclaringType().setBinaryPath(binaryPath);
         }

         if (binaryPath.indexOf("!") == -1) {
            File f = this.getDeclaringType().getSourceLocation().getSourceFile();
            int i = f.getPath().lastIndexOf(46);
            String path = null;
            if (i != -1) {
               path = f.getPath().substring(0, i) + ".class";
            } else {
               path = f.getPath() + ".class";
            }

            this.binaryFile = new File(binaryPath + "!" + path);
         } else {
            this.binaryFile = new File(binaryPath);
         }
      }

      return this.binaryFile;
   }

   public boolean isBinary() {
      if (!this.checkedIsBinary) {
         ResolvedType rt = this.getDeclaringType();
         if (rt != null) {
            this.isBinary = rt.getBinaryPath() != null;
         }

         this.checkedIsBinary = true;
      }

      return this.isBinary;
   }

   public abstract ShadowMunger concretize(ResolvedType var1, World var2, PerClause var3);

   public abstract void specializeOn(Shadow var1);

   public abstract boolean implementOn(Shadow var1);

   public abstract ShadowMunger parameterizeWith(ResolvedType var1, Map var2);

   public abstract Collection getThrownExceptions();

   public abstract boolean mustCheckExceptions();

   public void write(CompressingDataOutputStream stream) throws IOException {
      stream.writeInt(VERSION_1);
      stream.writeInt(this.shadowMungerKind);
      stream.writeInt(this.start);
      stream.writeInt(this.end);
      PersistenceSupport.write(stream, this.sourceContext);
      PersistenceSupport.write(stream, (Serializable)this.sourceLocation);
      PersistenceSupport.write(stream, (Serializable)this.binarySourceLocation);
      PersistenceSupport.write(stream, (Serializable)this.binaryFile);
      this.declaringType.write(stream);
      stream.writeBoolean(this.isBinary);
      stream.writeBoolean(this.checkedIsBinary);
      this.pointcut.write(stream);
   }
}
