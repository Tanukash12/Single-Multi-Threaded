package MultiThreaded;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsume(){
        /*
        return new Consumer<Socket>(){
            @Override
            void accept(Socket clientSocket){
                 try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the Server!");
                toClient.close();
                clientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        }
        */

        return (clientSocket) -> {
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the Server!");
                toClient.close();
                clientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket acceptedSocket = socket.accept();
                Thread thread = new Thread(()->{
                    server.getConsume().accept(acceptedSocket);
                });
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
