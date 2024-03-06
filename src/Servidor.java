import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket(12345);

        while (true) {
            try {
                //Crear un socket DatagramSocket

                //buffer para almacenar los datos
                byte[] receiveData = new byte[1024];

                //Crear un paquete para DatagramPacket para recibir los datos
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                //recibir el paquete(nos quedamos bloqueados esperando)
                socket.receive(receivePacket);
                System.out.println("Cliente conectado");
                HiloServidor hiloServidor = new HiloServidor(socket, receivePacket);
                Thread hilo = new Thread(hiloServidor);
                hilo.start();



            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


