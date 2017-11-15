package br.univali.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

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
    private Scanner s; 

    public Client(int port, String server, String name) 
    {
        try
        {
            this.port = port;
            this.name = name;
            this.server = server;

            socket = new DatagramSocket();
            address = InetAddress.getByName(this.server);
            
            s = new Scanner(System.in);
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

    public byte[] readMessage() 
    {
        System.out.println("Digite a mensagem para o servidor: ");
        String message = s.nextLine();
        return message.getBytes();
    }
    
    public void sendMessage(byte[] data) throws IOException
    {
        DatagramPacket sendBuffer = new DatagramPacket(data, data.length, address, port);
        socket.send(sendBuffer); //envio
    }
    
    public DatagramPacket receiveMessage() throws IOException
    {
         DatagramPacket receiveBuffer = new DatagramPacket(new byte[1024], 1024, address, port);
         socket.receive(receiveBuffer); //recepção
         return receiveBuffer;
    }
    
    public void printMessage(DatagramPacket receiveBuffer)
    {
        String receiveMessage = new String(receiveBuffer.getData());
        System.out.println("O servidor respondeu: " + receiveMessage);
    }

    public void run() {
        while (true) {
            try {
                byte[] data = readMessage(); //solicita a msg a ser enviada
                sendMessage(data); //envio dos dados para o emissor do datagrama recebido
                DatagramPacket receiveBuffer = receiveMessage(); //recebimento dos dados em um buffer de 1024 bytes
                printMessage(receiveBuffer); //imprime a msg recebida
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
