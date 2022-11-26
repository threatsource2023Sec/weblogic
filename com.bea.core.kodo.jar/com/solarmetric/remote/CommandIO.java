package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class CommandIO {
   private static final Localizer _loc = Localizer.forPackage(CommandIO.class);
   private static final StreamDecorator[] EMPTY_DECS = new StreamDecorator[0];
   private static final byte STATUS_OK = 0;
   private static final byte STATUS_ERROR = 1;
   private static final byte STATUS_TEST = 2;
   private Log _log = null;
   private ContextFactory _context = null;
   private StreamDecorator[] _streamDecs;

   public CommandIO() {
      this._streamDecs = EMPTY_DECS;
   }

   public CommandIO(ContextFactory context, Log log) {
      this._streamDecs = EMPTY_DECS;
      this._context = context;
      this._log = log;
   }

   public Log getLog() {
      return this._log;
   }

   public void setLog(Log log) {
      this._log = log;
   }

   public StreamDecorator[] getStreamDecorators() {
      return this._streamDecs;
   }

   public void setStreamDecorators(StreamDecorator[] streamDecs) {
      if (streamDecs == null) {
         streamDecs = EMPTY_DECS;
      }

      this._streamDecs = streamDecs;
   }

   public ContextFactory getContextFactory() {
      return this._context;
   }

   public void setContextFactory(ContextFactory context) {
      this._context = context;
   }

   public boolean execute(Transport.Channel channel) {
      InputStream ins = null;
      ObjectInput in = null;
      Command cmd = null;

      try {
         ins = channel.getInput();
         if (ins == null) {
            boolean var65 = false;
            return var65;
         }

         in = this.getObjectInput(this.decorate(ins));
         byte status = in.readByte();
         if (status == 2) {
            boolean var6 = true;
            return var6;
         }

         cmd = this.readType(in);
         cmd.setClientId(in.readLong());
         cmd.read(in);
         if (this._log != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("got-cmd", cmd));
         }
      } catch (TransportException var61) {
         throw var61;
      } catch (IOException var62) {
         channel.error(var62);
         throw new TransportException(var62);
      } catch (Exception var63) {
         throw new TransportException(var63);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var55) {
            }
         } else if (ins != null) {
            try {
               ins.close();
            } catch (Exception var54) {
            }
         }

      }

      Exception err = null;

      try {
         Object context = this._context == null ? null : this._context.getContext(cmd);
         cmd.execute(context);
      } catch (Exception var60) {
         if (this._log != null && this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("cmd-err", cmd, var60));
         }

         if (this._log != null && this._log.isTraceEnabled()) {
            this._log.trace(var60);
         }

         err = var60;
      }

      if (cmd.hasResponse()) {
         if (this._log != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("send-response", cmd));
         }

         OutputStream outs = null;
         ObjectOutput out = null;

         try {
            outs = channel.getOutput();
            out = this.getObjectOutput(this.decorate(outs));
            if (err != null) {
               out.writeByte(1);
               out.writeObject(err);
            } else {
               out.writeByte(0);
               cmd.writeResponse(out);
            }

            out.flush();
         } catch (TransportException var56) {
            throw var56;
         } catch (IOException var57) {
            channel.error(var57);
            throw new TransportException(var57);
         } catch (Exception var58) {
            throw new TransportException(var58);
         } finally {
            if (out != null) {
               try {
                  out.close();
               } catch (Exception var53) {
               }
            } else if (outs != null) {
               try {
                  outs.close();
               } catch (Exception var52) {
               }
            }

         }
      } else if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("no-response", cmd));
      }

      return true;
   }

   public void send(Command cmd, Transport.Channel channel) throws Exception {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("send-cmd", cmd));
      }

      OutputStream outs = null;
      ObjectOutput out = null;

      try {
         outs = channel.getOutput();
         out = this.getObjectOutput(this.decorate(outs));
         out.writeByte(0);
         this.writeType(cmd, out);
         out.writeLong(cmd.getClientId());
         cmd.write(out);
         out.flush();
      } catch (TransportException var48) {
         throw var48;
      } catch (IOException var49) {
         channel.error(var49);
         throw new TransportException(var49);
      } catch (Exception var50) {
         throw new TransportException(var50);
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (Exception var44) {
            }
         } else if (outs != null) {
            try {
               outs.close();
            } catch (Exception var43) {
            }
         }

      }

      if (cmd.hasResponse()) {
         InputStream ins = null;
         ObjectInput in = null;
         Exception err = null;

         try {
            ins = channel.getInput();
            in = this.getObjectInput(this.decorate(ins));
            byte status = in.readByte();
            if (status == 1) {
               err = (Exception)in.readObject();
            } else {
               cmd.readResponse(in);
            }
         } catch (TransportException var45) {
            throw var45;
         } catch (IOException var46) {
            channel.error(var46);
            throw new TransportException(var46);
         } catch (Exception var47) {
            throw new TransportException(var47);
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var42) {
               }
            } else if (ins != null) {
               try {
                  ins.close();
               } catch (Exception var41) {
               }
            }

         }

         if (err != null) {
            throw err;
         }
      }

   }

   public void test(Transport.Channel channel) throws Exception {
      OutputStream outs = null;
      ObjectOutput out = null;

      try {
         outs = channel.getOutput();
         out = this.getObjectOutput(this.decorate(outs));
         out.writeByte(2);
         out.flush();
      } catch (TransportException var17) {
         throw var17;
      } catch (IOException var18) {
         channel.error(var18);
         throw new TransportException(var18);
      } catch (Exception var19) {
         throw new TransportException(var19);
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (Exception var16) {
            }
         } else if (outs != null) {
            try {
               outs.close();
            } catch (Exception var15) {
            }
         }

      }

   }

   private InputStream decorate(InputStream in) throws Exception {
      for(int i = 0; i < this._streamDecs.length; ++i) {
         in = this._streamDecs[i].decorate(in);
      }

      return in;
   }

   private OutputStream decorate(OutputStream out) throws Exception {
      for(int i = 0; i < this._streamDecs.length; ++i) {
         out = this._streamDecs[i].decorate(out);
      }

      return out;
   }

   protected Command readType(ObjectInput in) throws Exception {
      String str = in.readUTF();
      return (Command)Class.forName(str).newInstance();
   }

   protected void writeType(Command cmd, ObjectOutput out) throws Exception {
      out.writeUTF(cmd.getClass().getName());
   }

   protected ObjectInput getObjectInput(InputStream in) throws Exception {
      return new ObjectInputStream(in);
   }

   protected ObjectOutput getObjectOutput(OutputStream out) throws Exception {
      return new ObjectOutputStream(out);
   }
}
