import java.io.IOException;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws SocketException {
        // Toda las operaciones con socket las metemos dentro de un try-catch
        DatagramSocket socket = null;
        try {
            // Crear un socket DatagramSocket
            socket = new DatagramSocket();

            // Datos a enviar
            String mensaje = "hola.txt leer";

            // Lo tenemos que pasar a un array de byte
            byte[] sendData = mensaje.getBytes();

            // Dirección IP del destinatario (servidor)
            InetAddress IPAdress = InetAddress.getByName("127.0.0.1");

            // Puerto del destinatario (servidor)
            int puerto = 12345;

            // Creamos un paquete de DatagramPacket para enviar los datos
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, puerto);

            // Enviamos los datos
            socket.send(sendPacket);

            //AHORA ESPERAMOS LA RESPUESTA DEL SERVIDOR
            // Buffer para almacenar los datos
            byte[] receiveData = new byte[1024];

            // Crear un paquete para DatagramPacket para recibir los datos
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket = new DatagramSocket(12344);
            // Recibir el paquete (nos quedamos bloqueados esperando)
            socket.receive(receivePacket);

            // Obtenemos los datos recibidos
            byte[] data = receivePacket.getData();
            String mensajeRecibido = new String(data, 0, receivePacket.getLength());

            // Imprimimos el mensaje recibido.
            System.out.println("Mensaje recibido:");
            System.out.println(mensajeRecibido);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Cerramos el socket al finalizar
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
