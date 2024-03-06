import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HiloServidor  implements Runnable{
    private DatagramSocket socket;
    private DatagramPacket receivePacket;
    public HiloServidor(DatagramSocket socket, DatagramPacket receivePacket) {
        this.socket=socket;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        //obtenemos los datos recibidos
        byte[] data = receivePacket.getData();
        String mensaje = new String(data, 0, receivePacket.getLength());
        String nombreFichero = mensaje.split(" ")[0];
        String opcion = mensaje.split(" ")[1];

        switch (opcion){
            case "leer":
                String mensajeRespuesta = leerFichero(nombreFichero);
                try {
                    //Crear un socket DatagramSocket
                    DatagramSocket socket = new DatagramSocket();

                    //Datos a enviar


                    //Lo tenemos que pasar a un array de byte
                    byte[] sendData = mensajeRespuesta.getBytes();

                    //Direccion ip del destinatario(servidor)
                    InetAddress IPAdress = InetAddress.getByName("127.0.0.1");

                    //Puerto del destinatario(servidor)
                    int puerto = 12344;

                    //Creamos un paquete de DatagramPacket para enviar los datos
                    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAdress,puerto);

                    //Enviamos los datos
                    socket.send(sendPacket);

                    //Cerramos el socket
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            break;
        }

    }

    private void mandarClienteRespuesta(String mensajeRespuesta) {
        //ESTO SERIA PARA ENVIAR DATOS DESDE EL CLIENTE AL SERVIDOR
        //Toda las operaciones con socket las metemos dentro de un try-catch
        try {
            //Crear un socket DatagramSocket
            DatagramSocket socket = new DatagramSocket();

            //Datos a enviar


            //Lo tenemos que pasar a un array de byte
            byte[] sendData = mensajeRespuesta.getBytes();

            //Direccion ip del destinatario(servidor)
            InetAddress IPAdress = InetAddress.getByName("127.0.0.1");

            //Puerto del destinatario(servidor)
            int puerto = 12345;

            //Creamos un paquete de DatagramPacket para enviar los datos
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAdress,puerto);

            //Enviamos los datos
            socket.send(sendPacket);

            //Cerramos el socket
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String leerFichero(String nombreFichero) {
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(nombreFichero));

            String linea = "";
            String mensaje = "";
            while ((linea = bfr.readLine())!=null){
                mensaje += linea + "\n";
            }
            bfr.close();

            return mensaje;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
