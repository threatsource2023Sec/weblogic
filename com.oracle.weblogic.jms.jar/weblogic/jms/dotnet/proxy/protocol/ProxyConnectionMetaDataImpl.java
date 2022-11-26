package weblogic.jms.dotnet.proxy.protocol;

import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyConnectionMetaDataImpl implements MarshalReadable, MarshalWritable, ConnectionMetaData {
   private static final int EXTVERSION = 1;
   private static final int _HAS_PROVIDER_NAME = 1;
   private static final int _HAS_JMSSPEC_VERSION = 2;
   private MarshalBitMask versionFlags;
   private int majorVersion;
   private int minorVersion;
   private String providerName;
   private String version;
   private String propertyNames;
   private int providerMajorVersion;
   private int providerMinorVersion;
   private String providerVersion;

   public ProxyConnectionMetaDataImpl() {
   }

   public ProxyConnectionMetaDataImpl(ConnectionMetaData metadata) throws JMSException {
      this.versionFlags = new MarshalBitMask(1);
      this.majorVersion = metadata.getJMSMajorVersion();
      this.minorVersion = metadata.getJMSMinorVersion();
      this.providerName = metadata.getJMSProviderName();
      if (this.providerName != null) {
         this.versionFlags.setBit(1);
      }

      this.version = metadata.getJMSVersion();
      if (this.version != null) {
         this.versionFlags.setBit(2);
      }

      this.propertyNames = "";
      Enumeration propNameEnumeration = metadata.getJMSXPropertyNames();

      for(int i = 0; propNameEnumeration.hasMoreElements(); this.propertyNames = this.propertyNames + (String)propNameEnumeration.nextElement()) {
         if (i++ > 0) {
            this.propertyNames = this.propertyNames + " ";
         }
      }

      this.providerMajorVersion = metadata.getProviderMajorVersion();
      this.providerMinorVersion = metadata.getProviderMinorVersion();
      this.providerVersion = metadata.getProviderVersion();
   }

   public int getMarshalTypeCode() {
      return 50;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags.marshal(mw);
      mw.writeInt(this.majorVersion);
      mw.writeInt(this.minorVersion);
      if (this.providerName != null) {
         mw.writeString(this.providerName);
      }

      if (this.version != null) {
         mw.writeString(this.version);
      }

      mw.writeString(this.propertyNames);
      mw.writeInt(this.providerMajorVersion);
      mw.writeInt(this.providerMinorVersion);
      mw.writeString(this.providerVersion);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.majorVersion = mr.readInt();
      this.minorVersion = mr.readInt();
      if (this.versionFlags.isSet(1)) {
         this.providerName = mr.readString();
      }

      if (this.versionFlags.isSet(2)) {
         this.version = mr.readString();
      }

      this.propertyNames = mr.readString();
      this.providerMajorVersion = mr.readInt();
      this.providerMinorVersion = mr.readInt();
      this.providerVersion = mr.readString();
   }

   public String toString() {
      return "ProxyConnectionMetaData<JMS Spec. version=" + this.version + ", JMS spec. major version=" + this.majorVersion + ", JMS spec. minor ver.=" + this.minorVersion + " Vendor=" + this.providerName + " Version= " + this.providerVersion + ", Provider major version=" + this.providerMajorVersion + ", Provider minor version=" + this.providerMinorVersion + " PropertyNames=" + this.propertyNames + " >";
   }

   public int getJMSMajorVersion() throws JMSException {
      return this.majorVersion;
   }

   public int getJMSMinorVersion() throws JMSException {
      return this.minorVersion;
   }

   public String getJMSProviderName() throws JMSException {
      return this.providerName;
   }

   public String getJMSVersion() throws JMSException {
      return this.version;
   }

   public Enumeration getJMSXPropertyNames() throws JMSException {
      StringTokenizer propertyNameTokenizer = new StringTokenizer(this.propertyNames);
      return propertyNameTokenizer;
   }

   public int getProviderMajorVersion() throws JMSException {
      return this.providerMajorVersion;
   }

   public int getProviderMinorVersion() throws JMSException {
      return this.providerMinorVersion;
   }

   public String getProviderVersion() throws JMSException {
      return this.providerVersion;
   }
}
