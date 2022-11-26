package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ONSConfigurationHelper {
   AddressList addresses = new AddressList();
   private String nodesVerbatim;
   private String walletFile;
   private String walletPassword;

   public static ONSConfigurationHelper create() {
      return new ONSConfigurationHelper();
   }

   ONSConfigurationHelper() {
   }

   public void addNode(String host, int port) {
      this.addresses.add(host, port);
   }

   public boolean removeNode(String host, int port) {
      return this.addresses.remove(host, port);
   }

   public String getNodes() {
      return this.nodesVerbatim != null ? this.nodesVerbatim : this.addresses.commaSeparatedList();
   }

   public void setNodes(String list) {
      if (list == null) {
         this.nodesVerbatim = null;
         this.addresses.clear();
      } else if (list.contains("=")) {
         this.addresses.clear();
         this.nodesVerbatim = list;
      } else {
         this.nodesVerbatim = null;
         this.addresses.setList(list);
      }

   }

   public void setWalletFile(String walletFile) {
      this.walletFile = walletFile;
   }

   public String getWalletFile() {
      return this.walletFile;
   }

   public void setWalletPassword(String walletPassword) {
      this.walletPassword = walletPassword;
   }

   public String getWalletPassword() {
      return this.walletPassword;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.nodesVerbatim != null) {
         sb.append(this.nodesVerbatim);
      } else {
         if (this.addresses.size() == 0) {
            return "";
         }

         sb.append("nodes=" + this.addresses.commaSeparatedList());
      }

      if (this.walletFile != null && this.walletFile.length() > 0) {
         sb.append("\n");
         sb.append("walletfile=" + this.walletFile);
         if (this.walletPassword != null) {
            sb.append("\n");
            sb.append("walletpassword=" + this.walletPassword);
         }
      }

      return sb.toString();
   }

   public static String[] parseNodeList(String nodelist) {
      if (nodelist == null) {
         return null;
      } else if (nodelist.contains("=")) {
         List hostports = new ArrayList();
         Properties nodeProps = new Properties();

         try {
            nodeProps.load(new StringReader(nodelist));
            Iterator var3 = nodeProps.stringPropertyNames().iterator();

            while(true) {
               String list;
               do {
                  do {
                     String key;
                     do {
                        if (!var3.hasNext()) {
                           return (String[])hostports.toArray(new String[hostports.size()]);
                        }

                        key = (String)var3.next();
                     } while(!key.startsWith("nodes"));

                     list = nodeProps.getProperty(key);
                  } while(list == null);
               } while(list.length() <= 0);

               String[] listarray = list.split("[\\s,]");
               String[] var7 = listarray;
               int var8 = listarray.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  String hostport = var7[var9];
                  hostports.add(hostport);
               }
            }
         } catch (IOException var11) {
            return null;
         }
      } else {
         return nodelist.split("[\\s,]");
      }
   }
}
