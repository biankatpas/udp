package br.univali.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author biankatpas
 */
public class Client 
{

    private int port;
    private String name;
    private String server;
    private DatagramSocket socket;
    private InetAddress address;

    public Client(int port, String server, String name) 
    {
        try 
        {
            this.port = port;
            this.name = name;
            this.server = server;
            
            socket = new DatagramSocket();
            address = InetAddress.getByName(this.server);
        } 
        catch (SocketException ex) 
        {
            System.err.println("socket error");
        } 
        catch (UnknownHostException ex) 
        {
            System.err.println("host error");
        }
    }

    public void run() 
    {
        while (true) 
        {
            try 
            {
                //envio de dados para o emissor do datagrama recebido
                String message = JOptionPane.showInputDialog(null, "Digite a mensagem do cliente "+name);
                byte[] data = message.getBytes();
                DatagramPacket sendBuffer = new DatagramPacket(data, data.length, address, port);
                socket.send(sendBuffer);//envio
                
                //recebimento dos dados em um buffer de 1024 bytes
                DatagramPacket receiveBuffer = new DatagramPacket(new byte[1024], 1024, address, port);
                socket.receive(receiveBuffer);//recepção
                
                //imprime a msg recebida
                String receiveMessage = new String(receiveBuffer.getData());
                System.out.println("O servidor disse: " + receiveMessage);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//    public static void main(String[] args) {
//        int port = 12345;
//        DatagramSocket socket = null;
//        try {
//            InetAddress address = InetAddress.getByName("127.0.0.1"); //broadcast "255.255.255.255"
//            socket = new DatagramSocket();
//            while (true) {
//                //envio de dados para o emissor do datagrama recebido
//                String message = JOptionPane.showInputDialog(null, "Digite a mensagem");
//                byte[] data = message.getBytes();
//                DatagramPacket sendBuffer = new DatagramPacket(data, data.length, address, port);
//                socket.send(sendBuffer);//envio
//                //encerra a conexao
//                if (message.equalsIgnoreCase("sair")) {
//                    socket.close();
//                }
//                //recebimento dos dados em um buffer de 1024 bytes
//                DatagramPacket receiveBuffer = new DatagramPacket(new byte[1024], 1024, address, port);
//                socket.receive(receiveBuffer);//recepção
//                //imprime a msg recebida
//                String receiveMessage = new String(receiveBuffer.getData());
//                System.out.println("O servidor disse: " + receiveMessage);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            if (socket != null) {
//                socket.close();
//            }
//        }
//    }
}
