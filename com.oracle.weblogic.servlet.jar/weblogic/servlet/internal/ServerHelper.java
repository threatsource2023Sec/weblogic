package weblogic.servlet.internal;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.servlet.utils.ServletObjectInputStream;
import weblogic.servlet.utils.ServletObjectOutputStream;

public final class ServerHelper {
   private static final String CLUSTER_LIST_IPV6_PREFIX = "ipv6_";
   private static final boolean useExtendedSessionFormat = "true".equalsIgnoreCase(System.getProperty("weblogic.servlet.useExtendedSessionFormat"));
   private static final Pattern ipv6regex = Pattern.compile("((:?([0-9a-fA-F]{0,4})){0,8})((\\.?[0-9]{1,3}){0,4})");

   public static final String createServerEntry(String channelName, ServerIdentity id, String delimiter) {
      return createServerEntry(channelName, id, delimiter, true);
   }

   public static final String createServerEntry(String channelName, ServerIdentity id, String delimiter, boolean useRaw) {
      ServerChannel channel = ServerChannelManager.findServerChannel(id, channelName);
      if (channel == null) {
         return null;
      } else {
         StringBuilder serverid = new StringBuilder();
         serverid.append(id.hashCode()).append(delimiter);
         if (useRaw) {
            serverid.append(getRawAddress(channel.getPublicAddress()));
         } else {
            serverid.append(channel.getPublicAddress());
         }

         serverid.append(delimiter);
         ServerChannel related;
         if (channel.supportsTLS()) {
            related = ServerChannelManager.findRelatedServerChannel(id, ProtocolHandlerHTTP.PROTOCOL_HTTP, channel.getPublicAddress());
            serverid.append(related == null ? -1 : related.getPublicPort()).append(delimiter);
            serverid.append(channel.getPublicPort());
         } else {
            related = ServerChannelManager.findRelatedServerChannel(id, ProtocolHandlerHTTPS.PROTOCOL_HTTPS, channel.getPublicAddress());
            serverid.append(channel.getPublicPort()).append(delimiter);
            serverid.append(related == null ? -1 : related.getPublicPort());
         }

         return serverid.toString();
      }
   }

   public static final String getRawAddress(String address) {
      try {
         InetAddress ia = InetAddress.getByName(address);
         boolean ipv6 = ia instanceof Inet6Address;
         if (ipv6) {
            if (!ipv6regex.matcher(address).matches()) {
               return address;
            }
         } else if (!ia.getHostAddress().equals(address)) {
            return address;
         }

         byte[] buf = ia.getAddress();
         if (ipv6) {
            BigInteger bigInt = new BigInteger(buf);
            return "ipv6_" + bigInt.toString();
         } else {
            long rawAddress = 0L;

            for(int i = 0; i < buf.length; ++i) {
               rawAddress += (long)((buf[buf.length - (i + 1)] & 255) << i * 8);
            }

            return Long.toString(rawAddress);
         }
      } catch (UnknownHostException var7) {
         return address;
      }
   }

   public static final boolean useExtendedSessionFormat() {
      return useExtendedSessionFormat;
   }

   public static final String getNetworkChannelName() {
      ServerChannel channel = weblogic.rmi.extensions.server.ServerHelper.getServerChannel();
      return channel != null ? channel.getChannelName() : "Default";
   }

   public static byte[] passivate(Object object) throws IOException {
      ServletObjectOutputStream out = ServletObjectOutputStream.getOutputStream();
      out.writeObject(object);
      return out.toByteArray();
   }

   public static Object activate(byte[] buf) throws IOException, ClassNotFoundException {
      ServletObjectInputStream in = ServletObjectInputStream.getInputStream(buf);
      return in.readObject();
   }

   public static byte[] zipBytes(byte[] passivated) {
      Deflater deflater = new Deflater(9);
      deflater.setInput(passivated);
      deflater.finish();
      int compressedDataSize = deflater.deflate(passivated);
      byte[] out = new byte[compressedDataSize];
      System.arraycopy(passivated, 0, out, 0, compressedDataSize);
      return out;
   }

   public static void unzipBytes(byte[] zipped, byte[] unzipped) throws DataFormatException {
      Inflater inflater = new Inflater();
      inflater.setInput(zipped, 0, zipped.length);
      inflater.end();
      inflater.inflate(unzipped);
   }
}
