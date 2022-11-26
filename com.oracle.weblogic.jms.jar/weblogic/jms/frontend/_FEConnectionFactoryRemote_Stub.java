package weblogic.jms.frontend;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import javax.jms.JMSException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.dispatcher.DispatcherWrapper;

public class _FEConnectionFactoryRemote_Stub extends Stub implements FEConnectionFactoryRemote {
   private static final String[] TYPE_IDS = new String[]{"RMI:weblogic.jms.frontend.FEConnectionFactoryRemote:0000000000000000"};

   public String[] _ids() {
      return TYPE_IDS;
   }

   public JMSConnection connectionCreate(DispatcherWrapper arg0) throws JMSException, RemoteException {
      try {
         InputStream in = null;

         JMSConnection var4;
         try {
            OutputStream out = (OutputStream)this._request("connectionCreate__weblogic_jms_dispatcher_DispatcherWrapper", true);
            out.write_value(arg0, DispatcherWrapper.class);
            in = (InputStream)this._invoke(out);
            var4 = (JMSConnection)in.read_value(JMSConnection.class);
            return var4;
         } catch (ApplicationException var10) {
            in = (InputStream)var10.getInputStream();
            String id = in.read_string();
            if (id.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)in.read_value(JMSException.class);
            }

            throw new UnexpectedException(id);
         } catch (RemarshalException var11) {
            var4 = this.connectionCreate(arg0);
         } finally {
            this._releaseReply(in);
         }

         return var4;
      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }

   public JMSConnection connectionCreate(DispatcherWrapper arg0, String arg1, String arg2) throws JMSException, RemoteException {
      try {
         InputStream in = null;

         JMSConnection var6;
         try {
            OutputStream out = (OutputStream)this._request("connectionCreate__weblogic_jms_dispatcher_DispatcherWrapper__CORBA_WStringValue__CORBA_WStringValue", true);
            out.write_value(arg0, DispatcherWrapper.class);
            out.write_value(arg1, String.class);
            out.write_value(arg2, String.class);
            in = (InputStream)this._invoke(out);
            var6 = (JMSConnection)in.read_value(JMSConnection.class);
            return var6;
         } catch (ApplicationException var12) {
            in = (InputStream)var12.getInputStream();
            String id = in.read_string();
            if (id.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)in.read_value(JMSException.class);
            }

            throw new UnexpectedException(id);
         } catch (RemarshalException var13) {
            var6 = this.connectionCreate(arg0, arg1, arg2);
         } finally {
            this._releaseReply(in);
         }

         return var6;
      } catch (SystemException var15) {
         throw Util.mapSystemException(var15);
      }
   }

   public JMSConnection connectionCreateRequest(FEConnectionCreateRequest arg0) throws JMSException, RemoteException {
      try {
         InputStream in = null;

         JMSConnection var4;
         try {
            OutputStream out = (OutputStream)this._request("connectionCreateRequest", true);
            out.write_value(arg0, FEConnectionCreateRequest.class);
            in = (InputStream)this._invoke(out);
            var4 = (JMSConnection)in.read_value(JMSConnection.class);
            return var4;
         } catch (ApplicationException var10) {
            in = (InputStream)var10.getInputStream();
            String id = in.read_string();
            if (id.equals("IDL:javax/jms/JMSEx:1.0")) {
               throw (JMSException)in.read_value(JMSException.class);
            }

            throw new UnexpectedException(id);
         } catch (RemarshalException var11) {
            var4 = this.connectionCreateRequest(arg0);
         } finally {
            this._releaseReply(in);
         }

         return var4;
      } catch (SystemException var13) {
         throw Util.mapSystemException(var13);
      }
   }
}
