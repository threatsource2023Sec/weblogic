package com.trilead.ssh2.transport;

import com.trilead.ssh2.DHGexParameters;
import com.trilead.ssh2.crypto.dh.DhExchange;
import com.trilead.ssh2.crypto.dh.DhGroupExchange;
import com.trilead.ssh2.packets.PacketKexInit;
import java.math.BigInteger;

public class KexState {
   public PacketKexInit localKEX;
   public PacketKexInit remoteKEX;
   public NegotiatedParameters np;
   public int state = 0;
   public BigInteger K;
   public byte[] H;
   public byte[] hostkey;
   public DhExchange dhx;
   public DhGroupExchange dhgx;
   public DHGexParameters dhgexParameters;
}
