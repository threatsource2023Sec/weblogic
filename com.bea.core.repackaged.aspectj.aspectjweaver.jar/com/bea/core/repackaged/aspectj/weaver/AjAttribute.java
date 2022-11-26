package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.Version;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.weaver.patterns.Declare;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public abstract class AjAttribute {
   public static final String AttributePrefix = "com.bea.core.repackaged.aspectj.weaver";

   protected abstract void write(CompressingDataOutputStream var1) throws IOException;

   public abstract String getNameString();

   public char[] getNameChars() {
      return this.getNameString().toCharArray();
   }

   public byte[] getBytes(ConstantPoolWriter compressor) {
      try {
         ByteArrayOutputStream b0 = new ByteArrayOutputStream();
         CompressingDataOutputStream s0 = new CompressingDataOutputStream(b0, compressor);
         this.write(s0);
         s0.close();
         return b0.toByteArray();
      } catch (IOException var4) {
         throw new RuntimeException("sanity check");
      }
   }

   public byte[] getAllBytes(short nameIndex, ConstantPoolWriter dataCompressor) {
      try {
         byte[] bytes = this.getBytes(dataCompressor);
         ByteArrayOutputStream b0 = new ByteArrayOutputStream();
         DataOutputStream s0 = new DataOutputStream(b0);
         s0.writeShort(nameIndex);
         s0.writeInt(bytes.length);
         s0.write(bytes);
         s0.close();
         return b0.toByteArray();
      } catch (IOException var6) {
         throw new RuntimeException("sanity check");
      }
   }

   public static AjAttribute read(WeaverVersionInfo v, String name, byte[] bytes, ISourceContext context, World w, ConstantPoolReader dataDecompressor) {
      try {
         if (bytes == null) {
            bytes = new byte[0];
         }

         VersionedDataInputStream s = new VersionedDataInputStream(new ByteArrayInputStream(bytes), dataDecompressor);
         s.setVersion(v);
         if (name.equals("com.bea.core.repackaged.aspectj.weaver.Aspect")) {
            return new Aspect(PerClause.readPerClause(s, context));
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.MethodDeclarationLineNumber")) {
            return AjAttribute.MethodDeclarationLineNumberAttribute.read(s);
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.WeaverState")) {
            return new WeaverState(WeaverStateInfo.read(s, context));
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.WeaverVersion")) {
            return AjAttribute.WeaverVersionInfo.read(s);
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.Advice")) {
            AdviceAttribute aa = AjAttribute.AdviceAttribute.read(s, context);
            aa.getPointcut().check(context, w);
            return aa;
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.PointcutDeclaration")) {
            PointcutDeclarationAttribute pda = new PointcutDeclarationAttribute(ResolvedPointcutDefinition.read(s, context));
            pda.pointcutDef.getPointcut().check(context, w);
            return pda;
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.TypeMunger")) {
            return new TypeMunger(ResolvedTypeMunger.read(s, context));
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.AjSynthetic")) {
            return new AjSynthetic();
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.Declare")) {
            return new DeclareAttribute(Declare.read(s, context));
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.Privileged")) {
            return AjAttribute.PrivilegedAttribute.read(s, context);
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.SourceContext")) {
            return AjAttribute.SourceContextAttribute.read(s);
         } else if (name.equals("com.bea.core.repackaged.aspectj.weaver.EffectiveSignature")) {
            return AjAttribute.EffectiveSignatureAttribute.read(s, context);
         } else if (w != null && w.getMessageHandler() != null) {
            w.getMessageHandler().handleMessage(MessageUtil.warn("unknown attribute encountered " + name));
            return null;
         } else {
            throw new BCException("unknown attribute" + name);
         }
      } catch (BCException var8) {
         throw new BCException("malformed " + name + " attribute (length:" + bytes.length + ")" + var8);
      } catch (IOException var9) {
         throw new BCException("malformed " + name + " attribute (length:" + bytes.length + ")" + var9);
      }
   }

   public static class EffectiveSignatureAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.EffectiveSignature";
      private final ResolvedMember effectiveSignature;
      private final Shadow.Kind shadowKind;
      private final boolean weaveBody;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.EffectiveSignature";
      }

      public EffectiveSignatureAttribute(ResolvedMember effectiveSignature, Shadow.Kind shadowKind, boolean weaveBody) {
         this.effectiveSignature = effectiveSignature;
         this.shadowKind = shadowKind;
         this.weaveBody = weaveBody;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.effectiveSignature.write(s);
         this.shadowKind.write(s);
         s.writeBoolean(this.weaveBody);
      }

      public static EffectiveSignatureAttribute read(VersionedDataInputStream s, ISourceContext context) throws IOException {
         ResolvedMember member = ResolvedMemberImpl.readResolvedMember(s, context);
         return new EffectiveSignatureAttribute(member, Shadow.Kind.read(s), s.readBoolean());
      }

      public ResolvedMember getEffectiveSignature() {
         return this.effectiveSignature;
      }

      public String toString() {
         return "EffectiveSignatureAttribute(" + this.effectiveSignature + ", " + this.shadowKind + ")";
      }

      public Shadow.Kind getShadowKind() {
         return this.shadowKind;
      }

      public boolean isWeaveBody() {
         return this.weaveBody;
      }
   }

   public static class PrivilegedAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.Privileged";
      private final ResolvedMember[] accessedMembers;

      public PrivilegedAttribute(ResolvedMember[] accessedMembers) {
         this.accessedMembers = accessedMembers;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         ResolvedMemberImpl.writeArray(this.accessedMembers, s);
      }

      public ResolvedMember[] getAccessedMembers() {
         return this.accessedMembers;
      }

      public static PrivilegedAttribute read(VersionedDataInputStream stream, ISourceContext context) throws IOException {
         PrivilegedAttribute pa = new PrivilegedAttribute(ResolvedMemberImpl.readResolvedMemberArray(stream, context));
         return pa;
      }

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.Privileged";
      }
   }

   public static class Aspect extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.Aspect";
      private final PerClause perClause;
      private IScope resolutionScope;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.Aspect";
      }

      public Aspect(PerClause perClause) {
         this.perClause = perClause;
      }

      public PerClause reify(ResolvedType inAspect) {
         return this.perClause;
      }

      public PerClause reifyFromAtAspectJ(ResolvedType inAspect) {
         this.perClause.resolve(this.resolutionScope);
         return this.perClause;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.perClause.write(s);
      }

      public void setResolutionScope(IScope binding) {
         this.resolutionScope = binding;
      }
   }

   public static class AdviceAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.Advice";
      private final AdviceKind kind;
      private final Pointcut pointcut;
      private final int extraParameterFlags;
      private final int start;
      private final int end;
      private final ISourceContext sourceContext;
      private boolean proceedInInners;
      private ResolvedMember[] proceedCallSignatures;
      private boolean[] formalsUnchangedToProceed;
      private UnresolvedType[] declaredExceptions;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.Advice";
      }

      public AdviceAttribute(AdviceKind kind, Pointcut pointcut, int extraArgumentFlags, int start, int end, ISourceContext sourceContext) {
         this.kind = kind;
         this.pointcut = pointcut;
         this.extraParameterFlags = extraArgumentFlags;
         this.start = start;
         this.end = end;
         this.sourceContext = sourceContext;
      }

      public AdviceAttribute(AdviceKind kind, Pointcut pointcut, int extraArgumentFlags, int start, int end, ISourceContext sourceContext, boolean proceedInInners, ResolvedMember[] proceedCallSignatures, boolean[] formalsUnchangedToProceed, UnresolvedType[] declaredExceptions) {
         this.kind = kind;
         this.pointcut = pointcut;
         this.extraParameterFlags = extraArgumentFlags;
         this.start = start;
         this.end = end;
         this.sourceContext = sourceContext;
         if (kind != AdviceKind.Around) {
            throw new IllegalArgumentException("only for around");
         } else {
            this.proceedInInners = proceedInInners;
            this.proceedCallSignatures = proceedCallSignatures;
            this.formalsUnchangedToProceed = formalsUnchangedToProceed;
            this.declaredExceptions = declaredExceptions;
         }
      }

      public static AdviceAttribute read(VersionedDataInputStream s, ISourceContext context) throws IOException {
         AdviceKind kind = AdviceKind.read(s);
         return kind == AdviceKind.Around ? new AdviceAttribute(kind, Pointcut.read(s, context), s.readByte(), s.readInt(), s.readInt(), context, s.readBoolean(), ResolvedMemberImpl.readResolvedMemberArray(s, context), FileUtil.readBooleanArray(s), UnresolvedType.readArray(s)) : new AdviceAttribute(kind, Pointcut.read(s, context), s.readByte(), s.readInt(), s.readInt(), context);
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.kind.write(s);
         this.pointcut.write(s);
         s.writeByte(this.extraParameterFlags);
         s.writeInt(this.start);
         s.writeInt(this.end);
         if (this.kind == AdviceKind.Around) {
            s.writeBoolean(this.proceedInInners);
            ResolvedMemberImpl.writeArray(this.proceedCallSignatures, s);
            FileUtil.writeBooleanArray(this.formalsUnchangedToProceed, s);
            UnresolvedType.writeArray(this.declaredExceptions, s);
         }

      }

      public Advice reify(Member signature, World world, ResolvedType concreteAspect) {
         return world.getWeavingSupport().createAdviceMunger(this, this.pointcut, signature, concreteAspect);
      }

      public String toString() {
         return "AdviceAttribute(" + this.kind + ", " + this.pointcut + ", " + this.extraParameterFlags + ", " + this.start + ")";
      }

      public int getExtraParameterFlags() {
         return this.extraParameterFlags;
      }

      public AdviceKind getKind() {
         return this.kind;
      }

      public Pointcut getPointcut() {
         return this.pointcut;
      }

      public UnresolvedType[] getDeclaredExceptions() {
         return this.declaredExceptions;
      }

      public boolean[] getFormalsUnchangedToProceed() {
         return this.formalsUnchangedToProceed;
      }

      public ResolvedMember[] getProceedCallSignatures() {
         return this.proceedCallSignatures;
      }

      public boolean isProceedInInners() {
         return this.proceedInInners;
      }

      public int getEnd() {
         return this.end;
      }

      public ISourceContext getSourceContext() {
         return this.sourceContext;
      }

      public int getStart() {
         return this.start;
      }
   }

   public static class DeclareAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.Declare";
      private final Declare declare;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.Declare";
      }

      public DeclareAttribute(Declare declare) {
         this.declare = declare;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.declare.write(s);
      }

      public Declare getDeclare() {
         return this.declare;
      }
   }

   public static class PointcutDeclarationAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.PointcutDeclaration";
      private final ResolvedPointcutDefinition pointcutDef;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.PointcutDeclaration";
      }

      public PointcutDeclarationAttribute(ResolvedPointcutDefinition pointcutDef) {
         this.pointcutDef = pointcutDef;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.pointcutDef.write(s);
      }

      public ResolvedPointcutDefinition reify() {
         return this.pointcutDef;
      }
   }

   public static class MethodDeclarationLineNumberAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.MethodDeclarationLineNumber";
      private final int lineNumber;
      private final int offset;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.MethodDeclarationLineNumber";
      }

      public MethodDeclarationLineNumberAttribute(int line, int offset) {
         this.lineNumber = line;
         this.offset = offset;
      }

      public int getLineNumber() {
         return this.lineNumber;
      }

      public int getOffset() {
         return this.offset;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         s.writeInt(this.lineNumber);
         s.writeInt(this.offset);
      }

      public static MethodDeclarationLineNumberAttribute read(VersionedDataInputStream s) throws IOException {
         int line = s.readInt();
         int offset = 0;
         if (s.available() > 0) {
            offset = s.readInt();
         }

         return new MethodDeclarationLineNumberAttribute(line, offset);
      }

      public String toString() {
         return "org.aspectj.weaver.MethodDeclarationLineNumber: " + this.lineNumber + ":" + this.offset;
      }
   }

   public static class SourceContextAttribute extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.SourceContext";
      private final String sourceFileName;
      private final int[] lineBreaks;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.SourceContext";
      }

      public SourceContextAttribute(String sourceFileName, int[] lineBreaks) {
         this.sourceFileName = sourceFileName;
         this.lineBreaks = lineBreaks;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         if (s.canCompress()) {
            s.writeCompressedPath(this.sourceFileName);
         } else {
            s.writeUTF(this.sourceFileName);
         }

         s.writeInt(this.lineBreaks.length);
         int previous = 0;
         int i = 0;

         for(int max = this.lineBreaks.length; i < max; ++i) {
            s.writeShort(this.lineBreaks[i] - previous);
            previous = this.lineBreaks[i];
         }

      }

      public static SourceContextAttribute read(VersionedDataInputStream s) throws IOException {
         String sourceFileName = s.isAtLeast169() ? s.readPath() : s.readUTF();
         int lineBreaks = s.readInt();
         int[] lines = new int[lineBreaks];
         int previous = 0;

         for(int i = 0; i < lineBreaks; ++i) {
            if (s.isAtLeast169()) {
               lines[i] = s.readShort() + previous;
               previous = lines[i];
            } else {
               lines[i] = s.readInt();
            }
         }

         return new SourceContextAttribute(sourceFileName, lines);
      }

      public int[] getLineBreaks() {
         return this.lineBreaks;
      }

      public String getSourceFileName() {
         return this.sourceFileName;
      }
   }

   public static class WeaverVersionInfo extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.WeaverVersion";
      public static final short WEAVER_VERSION_MAJOR_UNKNOWN = 0;
      public static final short WEAVER_VERSION_MINOR_UNKNOWN = 0;
      public static final short WEAVER_VERSION_MAJOR_AJ121 = 1;
      public static final short WEAVER_VERSION_MINOR_AJ121 = 0;
      public static final short WEAVER_VERSION_MAJOR_AJ150M4 = 3;
      public static final short WEAVER_VERSION_MAJOR_AJ150 = 2;
      public static final short WEAVER_VERSION_MINOR_AJ150 = 0;
      public static final short WEAVER_VERSION_MAJOR_AJ160M2 = 5;
      public static final short WEAVER_VERSION_MAJOR_AJ160 = 4;
      public static final short WEAVER_VERSION_MINOR_AJ160 = 0;
      public static final short WEAVER_VERSION_MAJOR_AJ161 = 6;
      public static final short WEAVER_VERSION_MINOR_AJ161 = 0;
      public static final short WEAVER_VERSION_AJ169 = 7;
      private static final short CURRENT_VERSION_MAJOR = 7;
      private static final short CURRENT_VERSION_MINOR = 0;
      public static final WeaverVersionInfo UNKNOWN = new WeaverVersionInfo((short)0, (short)0);
      public static final WeaverVersionInfo CURRENT = new WeaverVersionInfo((short)7, (short)0);
      private final short major_version;
      private final short minor_version;
      private long buildstamp = 0L;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.WeaverVersion";
      }

      public WeaverVersionInfo() {
         this.major_version = 7;
         this.minor_version = 0;
      }

      public WeaverVersionInfo(short major, short minor) {
         this.major_version = major;
         this.minor_version = minor;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         s.writeShort(7);
         s.writeShort(0);
         s.writeLong(Version.getTime());
      }

      public static WeaverVersionInfo read(VersionedDataInputStream s) throws IOException {
         short major = s.readShort();
         short minor = s.readShort();
         WeaverVersionInfo wvi = new WeaverVersionInfo(major, minor);
         if (s.getMajorVersion() >= 3) {
            long stamp = 0L;

            try {
               stamp = s.readLong();
               wvi.setBuildstamp(stamp);
            } catch (EOFException var7) {
            }
         }

         return wvi;
      }

      public short getMajorVersion() {
         return this.major_version;
      }

      public short getMinorVersion() {
         return this.minor_version;
      }

      public static short getCurrentWeaverMajorVersion() {
         return 7;
      }

      public static short getCurrentWeaverMinorVersion() {
         return 0;
      }

      public void setBuildstamp(long stamp) {
         this.buildstamp = stamp;
      }

      public long getBuildstamp() {
         return this.buildstamp;
      }

      public String toString() {
         return this.major_version + "." + this.minor_version;
      }

      public static String toCurrentVersionString() {
         return "7.0";
      }
   }

   public static class WeaverState extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.WeaverState";
      private final WeaverStateInfo kind;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.WeaverState";
      }

      public WeaverState(WeaverStateInfo kind) {
         this.kind = kind;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.kind.write(s);
      }

      public WeaverStateInfo reify() {
         return this.kind;
      }
   }

   public static class TypeMunger extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.TypeMunger";
      private final ResolvedTypeMunger munger;

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.TypeMunger";
      }

      public TypeMunger(ResolvedTypeMunger munger) {
         this.munger = munger;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.munger.write(s);
      }

      public ConcreteTypeMunger reify(World world, ResolvedType aspectType) {
         return world.getWeavingSupport().concreteTypeMunger(this.munger, aspectType);
      }
   }

   public static class AjSynthetic extends AjAttribute {
      public static final String AttributeName = "com.bea.core.repackaged.aspectj.weaver.AjSynthetic";

      public String getNameString() {
         return "com.bea.core.repackaged.aspectj.weaver.AjSynthetic";
      }

      public void write(CompressingDataOutputStream s) throws IOException {
      }
   }
}
