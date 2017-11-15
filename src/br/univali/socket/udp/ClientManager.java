package br.univali.socket.udp;

/**
 *
 * @author Angelita
 */
public class ClientManager {
    public static void main(String[] args) {
        new Client(12345, "127.0.0.1", "A").run();
    }
}
