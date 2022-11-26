package com.trilead.ssh2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SCPClient {
   Connection conn;

   public SCPClient(Connection conn) {
      if (conn == null) {
         throw new IllegalArgumentException("Cannot accept null argument!");
      } else {
         this.conn = conn;
      }
   }

   private void readResponse(InputStream is) throws IOException {
      int c = is.read();
      if (c != 0) {
         if (c == -1) {
            throw new IOException("Remote scp terminated unexpectedly.");
         } else if (c != 1 && c != 2) {
            throw new IOException("Remote scp sent illegal error code.");
         } else if (c == 2) {
            throw new IOException("Remote scp terminated with error.");
         } else {
            String err = this.receiveLine(is);
            throw new IOException("Remote scp terminated with error (" + err + ").");
         }
      }
   }

   private String receiveLine(InputStream is) throws IOException {
      StringBuffer sb = new StringBuffer(30);

      while(sb.length() <= 8192) {
         int c = is.read();
         if (c < 0) {
            throw new IOException("Remote scp terminated unexpectedly.");
         }

         if (c == 10) {
            return sb.toString();
         }

         sb.append((char)c);
      }

      throw new IOException("Remote scp sent a too long line");
   }

   private LenNamePair parseCLine(String line) throws IOException {
      if (line.length() < 8) {
         throw new IOException("Malformed C line sent by remote SCP binary, line too short.");
      } else if (line.charAt(4) == ' ' && line.charAt(5) != ' ') {
         int length_name_sep = line.indexOf(32, 5);
         if (length_name_sep == -1) {
            throw new IOException("Malformed C line sent by remote SCP binary.");
         } else {
            String length_substring = line.substring(5, length_name_sep);
            String name_substring = line.substring(length_name_sep + 1);
            if (length_substring.length() > 0 && name_substring.length() > 0) {
               if (6 + length_substring.length() + name_substring.length() != line.length()) {
                  throw new IOException("Malformed C line sent by remote SCP binary.");
               } else {
                  long len;
                  try {
                     len = Long.parseLong(length_substring);
                  } catch (NumberFormatException var8) {
                     throw new IOException("Malformed C line sent by remote SCP binary, cannot parse file length.");
                  }

                  if (len < 0L) {
                     throw new IOException("Malformed C line sent by remote SCP binary, illegal file length.");
                  } else {
                     LenNamePair lnp = new LenNamePair();
                     lnp.length = len;
                     lnp.filename = name_substring;
                     return lnp;
                  }
               }
            } else {
               throw new IOException("Malformed C line sent by remote SCP binary.");
            }
         }
      } else {
         throw new IOException("Malformed C line sent by remote SCP binary.");
      }
   }

   private void sendBytes(Session sess, byte[] data, String fileName, String mode) throws IOException {
      OutputStream os = sess.getStdin();
      InputStream is = new BufferedInputStream(sess.getStdout(), 512);
      this.readResponse(is);
      String cline = "C" + mode + " " + data.length + " " + fileName + "\n";
      os.write(cline.getBytes());
      os.flush();
      this.readResponse(is);
      os.write(data, 0, data.length);
      os.write(0);
      os.flush();
      this.readResponse(is);
      os.write("E\n".getBytes());
      os.flush();
   }

   private void sendFiles(Session sess, String[] files, String[] remoteFiles, String mode) throws IOException {
      byte[] buffer = new byte[8192];
      OutputStream os = new BufferedOutputStream(sess.getStdin(), 40000);
      InputStream is = new BufferedInputStream(sess.getStdout(), 512);
      this.readResponse(is);

      for(int i = 0; i < files.length; ++i) {
         File f = new File(files[i]);
         long remain = f.length();
         String remoteName;
         if (remoteFiles != null && remoteFiles.length > i && remoteFiles[i] != null) {
            remoteName = remoteFiles[i];
         } else {
            remoteName = f.getName();
         }

         String cline = "C" + mode + " " + remain + " " + remoteName + "\n";
         os.write(cline.getBytes());
         os.flush();
         this.readResponse(is);
         FileInputStream fis = null;

         int trans;
         try {
            for(fis = new FileInputStream(f); remain > 0L; remain -= (long)trans) {
               if (remain > (long)buffer.length) {
                  trans = buffer.length;
               } else {
                  trans = (int)remain;
               }

               if (fis.read(buffer, 0, trans) != trans) {
                  throw new IOException("Cannot read enough from local file " + files[i]);
               }

               os.write(buffer, 0, trans);
            }
         } finally {
            if (fis != null) {
               fis.close();
            }

         }

         os.write(0);
         os.flush();
         this.readResponse(is);
      }

      os.write("E\n".getBytes());
      os.flush();
   }

   private void receiveFiles(Session sess, OutputStream[] targets) throws IOException {
      byte[] buffer = new byte[8192];
      OutputStream os = new BufferedOutputStream(sess.getStdin(), 512);
      InputStream is = new BufferedInputStream(sess.getStdout(), 40000);
      os.write(0);
      os.flush();

      for(int i = 0; i < targets.length; ++i) {
         LenNamePair lnp = null;

         int c;
         String line;
         do {
            c = is.read();
            if (c < 0) {
               throw new IOException("Remote scp terminated unexpectedly.");
            }

            line = this.receiveLine(is);
         } while(c == 84);

         if (c == 1 || c == 2) {
            throw new IOException("Remote SCP error: " + line);
         }

         if (c != 67) {
            throw new IOException("Remote SCP error: " + (char)c + line);
         }

         lnp = this.parseCLine(line);
         os.write(0);
         os.flush();

         int this_time_received;
         for(long remain = lnp.length; remain > 0L; remain -= (long)this_time_received) {
            int trans;
            if (remain > (long)buffer.length) {
               trans = buffer.length;
            } else {
               trans = (int)remain;
            }

            this_time_received = is.read(buffer, 0, trans);
            if (this_time_received < 0) {
               throw new IOException("Remote scp terminated connection unexpectedly");
            }

            targets[i].write(buffer, 0, this_time_received);
         }

         this.readResponse(is);
         os.write(0);
         os.flush();
      }

   }

   private void receiveFiles(Session sess, String[] files, String target) throws IOException {
      byte[] buffer = new byte[8192];
      OutputStream os = new BufferedOutputStream(sess.getStdin(), 512);
      InputStream is = new BufferedInputStream(sess.getStdout(), 40000);
      os.write(0);
      os.flush();
      int i = 0;

      while(i < files.length) {
         LenNamePair lnp = null;

         int c;
         String line;
         do {
            c = is.read();
            if (c < 0) {
               throw new IOException("Remote scp terminated unexpectedly.");
            }

            line = this.receiveLine(is);
         } while(c == 84);

         if (c != 1 && c != 2) {
            if (c == 67) {
               lnp = this.parseCLine(line);
               os.write(0);
               os.flush();
               File f = new File(target + File.separatorChar + lnp.filename);
               FileOutputStream fop = null;

               try {
                  fop = new FileOutputStream(f);

                  int this_time_received;
                  for(long remain = lnp.length; remain > 0L; remain -= (long)this_time_received) {
                     int trans;
                     if (remain > (long)buffer.length) {
                        trans = buffer.length;
                     } else {
                        trans = (int)remain;
                     }

                     this_time_received = is.read(buffer, 0, trans);
                     if (this_time_received < 0) {
                        throw new IOException("Remote scp terminated connection unexpectedly");
                     }

                     fop.write(buffer, 0, this_time_received);
                  }
               } finally {
                  if (fop != null) {
                     fop.close();
                  }

               }

               this.readResponse(is);
               os.write(0);
               os.flush();
               ++i;
               continue;
            }

            throw new IOException("Remote SCP error: " + (char)c + line);
         }

         throw new IOException("Remote SCP error: " + line);
      }

   }

   public void put(String localFile, String remoteTargetDirectory) throws IOException {
      this.put(new String[]{localFile}, remoteTargetDirectory, "0600");
   }

   public void put(String[] localFiles, String remoteTargetDirectory) throws IOException {
      this.put(localFiles, remoteTargetDirectory, "0600");
   }

   public void put(String localFile, String remoteTargetDirectory, String mode) throws IOException {
      this.put(new String[]{localFile}, remoteTargetDirectory, mode);
   }

   public void put(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) throws IOException {
      this.put(new String[]{localFile}, new String[]{remoteFileName}, remoteTargetDirectory, mode);
   }

   public void put(byte[] data, String remoteFileName, String remoteTargetDirectory) throws IOException {
      this.put(data, remoteFileName, remoteTargetDirectory, "0600");
   }

   public void put(byte[] data, String remoteFileName, String remoteTargetDirectory, String mode) throws IOException {
      Session sess = null;
      if (remoteFileName != null && remoteTargetDirectory != null && mode != null) {
         if (mode.length() != 4) {
            throw new IllegalArgumentException("Invalid mode.");
         } else {
            for(int i = 0; i < mode.length(); ++i) {
               if (!Character.isDigit(mode.charAt(i))) {
                  throw new IllegalArgumentException("Invalid mode.");
               }
            }

            remoteTargetDirectory = remoteTargetDirectory.trim();
            remoteTargetDirectory = remoteTargetDirectory.length() > 0 ? remoteTargetDirectory : ".";
            String cmd = "scp -t -d " + remoteTargetDirectory;

            try {
               sess = this.conn.openSession();
               sess.execCommand(cmd);
               this.sendBytes(sess, data, remoteFileName, mode);
            } catch (IOException var11) {
               throw (IOException)(new IOException("Error during SCP transfer.")).initCause(var11);
            } finally {
               if (sess != null) {
                  sess.close();
               }

            }

         }
      } else {
         throw new IllegalArgumentException("Null argument.");
      }
   }

   public void put(String[] localFiles, String remoteTargetDirectory, String mode) throws IOException {
      this.put((String[])localFiles, (String[])null, remoteTargetDirectory, mode);
   }

   public void put(String[] localFiles, String[] remoteFiles, String remoteTargetDirectory, String mode) throws IOException {
      Session sess = null;
      if (localFiles != null && remoteTargetDirectory != null && mode != null) {
         if (mode.length() != 4) {
            throw new IllegalArgumentException("Invalid mode.");
         } else {
            for(int i = 0; i < mode.length(); ++i) {
               if (!Character.isDigit(mode.charAt(i))) {
                  throw new IllegalArgumentException("Invalid mode.");
               }
            }

            if (localFiles.length != 0) {
               remoteTargetDirectory = remoteTargetDirectory.trim();
               remoteTargetDirectory = remoteTargetDirectory.length() > 0 ? remoteTargetDirectory : ".";
               String cmd = "scp -t -d " + remoteTargetDirectory;

               for(int i = 0; i < localFiles.length; ++i) {
                  if (localFiles[i] == null) {
                     throw new IllegalArgumentException("Cannot accept null filename.");
                  }
               }

               try {
                  sess = this.conn.openSession();
                  sess.execCommand(cmd);
                  this.sendFiles(sess, localFiles, remoteFiles, mode);
               } catch (IOException var11) {
                  throw (IOException)(new IOException("Error during SCP transfer.")).initCause(var11);
               } finally {
                  if (sess != null) {
                     sess.close();
                  }

               }

            }
         }
      } else {
         throw new IllegalArgumentException("Null argument.");
      }
   }

   public void get(String remoteFile, String localTargetDirectory) throws IOException {
      this.get(new String[]{remoteFile}, localTargetDirectory);
   }

   public void get(String remoteFile, OutputStream target) throws IOException {
      this.get(new String[]{remoteFile}, new OutputStream[]{target});
   }

   private void get(String[] remoteFiles, OutputStream[] targets) throws IOException {
      Session sess = null;
      if (remoteFiles != null && targets != null) {
         if (remoteFiles.length != targets.length) {
            throw new IllegalArgumentException("Length of arguments does not match.");
         } else if (remoteFiles.length != 0) {
            String cmd = "scp -f";

            for(int i = 0; i < remoteFiles.length; ++i) {
               if (remoteFiles[i] == null) {
                  throw new IllegalArgumentException("Cannot accept null filename.");
               }

               String tmp = remoteFiles[i].trim();
               if (tmp.length() == 0) {
                  throw new IllegalArgumentException("Cannot accept empty filename.");
               }

               cmd = cmd + " " + tmp;
            }

            try {
               sess = this.conn.openSession();
               sess.execCommand(cmd);
               this.receiveFiles(sess, targets);
            } catch (IOException var10) {
               throw (IOException)(new IOException("Error during SCP transfer.")).initCause(var10);
            } finally {
               if (sess != null) {
                  sess.close();
               }

            }

         }
      } else {
         throw new IllegalArgumentException("Null argument.");
      }
   }

   public void get(String[] remoteFiles, String localTargetDirectory) throws IOException {
      Session sess = null;
      if (remoteFiles != null && localTargetDirectory != null) {
         if (remoteFiles.length != 0) {
            String cmd = "scp -f";

            for(int i = 0; i < remoteFiles.length; ++i) {
               if (remoteFiles[i] == null) {
                  throw new IllegalArgumentException("Cannot accept null filename.");
               }

               String tmp = remoteFiles[i].trim();
               if (tmp.length() == 0) {
                  throw new IllegalArgumentException("Cannot accept empty filename.");
               }

               cmd = cmd + " " + tmp;
            }

            try {
               sess = this.conn.openSession();
               sess.execCommand(cmd);
               this.receiveFiles(sess, remoteFiles, localTargetDirectory);
            } catch (IOException var10) {
               throw (IOException)(new IOException("Error during SCP transfer.")).initCause(var10);
            } finally {
               if (sess != null) {
                  sess.close();
               }

            }

         }
      } else {
         throw new IllegalArgumentException("Null argument.");
      }
   }

   class LenNamePair {
      long length;
      String filename;
   }
}
