package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.util.Arrays;

public abstract class AbstractTlsServer extends AbstractTlsPeer implements TlsServer {
   protected TlsCipherFactory cipherFactory;
   protected TlsServerContext context;
   protected ProtocolVersion clientVersion;
   protected int[] offeredCipherSuites;
   protected short[] offeredCompressionMethods;
   protected Hashtable clientExtensions;
   protected boolean encryptThenMACOffered;
   protected short maxFragmentLengthOffered;
   protected boolean truncatedHMacOffered;
   protected Vector supportedSignatureAlgorithms;
   protected boolean eccCipherSuitesOffered;
   protected int[] namedCurves;
   protected short[] clientECPointFormats;
   protected short[] serverECPointFormats;
   protected ProtocolVersion serverVersion;
   protected int selectedCipherSuite;
   protected short selectedCompressionMethod;
   protected Hashtable serverExtensions;

   public AbstractTlsServer() {
      this(new DefaultTlsCipherFactory());
   }

   public AbstractTlsServer(TlsCipherFactory var1) {
      this.cipherFactory = var1;
   }

   protected boolean allowEncryptThenMAC() {
      return true;
   }

   protected boolean allowTruncatedHMac() {
      return false;
   }

   protected Hashtable checkServerExtensions() {
      return this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.serverExtensions);
   }

   protected abstract int[] getCipherSuites();

   protected short[] getCompressionMethods() {
      return new short[]{0};
   }

   protected ProtocolVersion getMaximumVersion() {
      return ProtocolVersion.TLSv11;
   }

   protected ProtocolVersion getMinimumVersion() {
      return ProtocolVersion.TLSv10;
   }

   protected boolean supportsClientECCCapabilities(int[] var1, short[] var2) {
      if (var1 == null) {
         return TlsECCUtils.hasAnySupportedNamedCurves();
      } else {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            int var4 = var1[var3];
            if (NamedCurve.isValid(var4) && (!NamedCurve.refersToASpecificNamedCurve(var4) || TlsECCUtils.isSupportedNamedCurve(var4))) {
               return true;
            }
         }

         return false;
      }
   }

   public void init(TlsServerContext var1) {
      this.context = var1;
   }

   public void notifyClientVersion(ProtocolVersion var1) throws IOException {
      this.clientVersion = var1;
   }

   public void notifyFallback(boolean var1) throws IOException {
      if (var1 && this.getMaximumVersion().isLaterVersionOf(this.clientVersion)) {
         throw new TlsFatalAlert((short)86);
      }
   }

   public void notifyOfferedCipherSuites(int[] var1) throws IOException {
      this.offeredCipherSuites = var1;
      this.eccCipherSuitesOffered = TlsECCUtils.containsECCCipherSuites(this.offeredCipherSuites);
   }

   public void notifyOfferedCompressionMethods(short[] var1) throws IOException {
      this.offeredCompressionMethods = var1;
   }

   public void processClientExtensions(Hashtable var1) throws IOException {
      this.clientExtensions = var1;
      if (var1 != null) {
         this.encryptThenMACOffered = TlsExtensionsUtils.hasEncryptThenMACExtension(var1);
         this.maxFragmentLengthOffered = TlsExtensionsUtils.getMaxFragmentLengthExtension(var1);
         if (this.maxFragmentLengthOffered >= 0 && !MaxFragmentLength.isValid(this.maxFragmentLengthOffered)) {
            throw new TlsFatalAlert((short)47);
         }

         this.truncatedHMacOffered = TlsExtensionsUtils.hasTruncatedHMacExtension(var1);
         this.supportedSignatureAlgorithms = TlsUtils.getSignatureAlgorithmsExtension(var1);
         if (this.supportedSignatureAlgorithms != null && !TlsUtils.isSignatureAlgorithmsExtensionAllowed(this.clientVersion)) {
            throw new TlsFatalAlert((short)47);
         }

         this.namedCurves = TlsECCUtils.getSupportedEllipticCurvesExtension(var1);
         this.clientECPointFormats = TlsECCUtils.getSupportedPointFormatsExtension(var1);
      }

   }

   public ProtocolVersion getServerVersion() throws IOException {
      if (this.getMinimumVersion().isEqualOrEarlierVersionOf(this.clientVersion)) {
         ProtocolVersion var1 = this.getMaximumVersion();
         if (this.clientVersion.isEqualOrEarlierVersionOf(var1)) {
            return this.serverVersion = this.clientVersion;
         }

         if (this.clientVersion.isLaterVersionOf(var1)) {
            return this.serverVersion = var1;
         }
      }

      throw new TlsFatalAlert((short)70);
   }

   public int getSelectedCipherSuite() throws IOException {
      Vector var1 = TlsUtils.getUsableSignatureAlgorithms(this.supportedSignatureAlgorithms);
      boolean var2 = this.supportsClientECCCapabilities(this.namedCurves, this.clientECPointFormats);
      int[] var3 = this.getCipherSuites();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         int var5 = var3[var4];
         if (Arrays.contains(this.offeredCipherSuites, var5) && (var2 || !TlsECCUtils.isECCCipherSuite(var5)) && TlsUtils.isValidCipherSuiteForVersion(var5, this.serverVersion) && TlsUtils.isValidCipherSuiteForSignatureAlgorithms(var5, var1)) {
            return this.selectedCipherSuite = var5;
         }
      }

      throw new TlsFatalAlert((short)40);
   }

   public short getSelectedCompressionMethod() throws IOException {
      short[] var1 = this.getCompressionMethods();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (Arrays.contains(this.offeredCompressionMethods, var1[var2])) {
            return this.selectedCompressionMethod = var1[var2];
         }
      }

      throw new TlsFatalAlert((short)40);
   }

   public Hashtable getServerExtensions() throws IOException {
      if (this.encryptThenMACOffered && this.allowEncryptThenMAC() && TlsUtils.isBlockCipherSuite(this.selectedCipherSuite)) {
         TlsExtensionsUtils.addEncryptThenMACExtension(this.checkServerExtensions());
      }

      if (this.maxFragmentLengthOffered >= 0 && MaxFragmentLength.isValid(this.maxFragmentLengthOffered)) {
         TlsExtensionsUtils.addMaxFragmentLengthExtension(this.checkServerExtensions(), this.maxFragmentLengthOffered);
      }

      if (this.truncatedHMacOffered && this.allowTruncatedHMac()) {
         TlsExtensionsUtils.addTruncatedHMacExtension(this.checkServerExtensions());
      }

      if (this.clientECPointFormats != null && TlsECCUtils.isECCCipherSuite(this.selectedCipherSuite)) {
         this.serverECPointFormats = new short[]{0, 1, 2};
         TlsECCUtils.addSupportedPointFormatsExtension(this.checkServerExtensions(), this.serverECPointFormats);
      }

      return this.serverExtensions;
   }

   public Vector getServerSupplementalData() throws IOException {
      return null;
   }

   public CertificateStatus getCertificateStatus() throws IOException {
      return null;
   }

   public CertificateRequest getCertificateRequest() throws IOException {
      return null;
   }

   public void processClientSupplementalData(Vector var1) throws IOException {
      if (var1 != null) {
         throw new TlsFatalAlert((short)10);
      }
   }

   public void notifyClientCertificate(Certificate var1) throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   public TlsCompression getCompression() throws IOException {
      switch (this.selectedCompressionMethod) {
         case 0:
            return new TlsNullCompression();
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   public TlsCipher getCipher() throws IOException {
      int var1 = TlsUtils.getEncryptionAlgorithm(this.selectedCipherSuite);
      int var2 = TlsUtils.getMACAlgorithm(this.selectedCipherSuite);
      return this.cipherFactory.createCipher(this.context, var1, var2);
   }

   public NewSessionTicket getNewSessionTicket() throws IOException {
      return new NewSessionTicket(0L, TlsUtils.EMPTY_BYTES);
   }
}
