package br.univali.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author biankatpas
 */
public class Server {

    public static void main(String[] args) {
        int porta = 12345;
        DatagramSocket socket = null;
        boolean run = true;

        try {
            System.out.println("Iniciando o servidor...");
            socket = new DatagramSocket(porta);
            System.out.println("Servidor iniciado...");

            String msgEnviada = "";
            while (run) {
                //recebimento dos dados em um datagrama de 1024 bytes
                DatagramPacket bufferRecebimento = new DatagramPacket(new byte[1024], 1024);
                socket.receive(bufferRecebimento); //recepção

                //imprime o dado do datagrama recebido
                String msgRecebida = new String(bufferRecebimento.getData());
                System.out.println("O cliente disse: " + msgRecebida);

                //envio de dados para o emissor do datagrama recebido
                msgEnviada += msgRecebida;
                DatagramPacket bufferEnvio = new DatagramPacket(msgEnviada.getBytes(), msgEnviada.getBytes().length, bufferRecebimento.getAddress(), bufferRecebimento.getPort());
                socket.send(bufferEnvio);
            }
        } catch (Exception e) {
            System.out.println(e);
            if (socket != null) {
                socket.close();
            }
        }
    }
}
