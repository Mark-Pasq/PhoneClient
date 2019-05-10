
import java.util.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.String;
import java.net.Socket;


public class Client {

    public static void main (String[] args ) throws Exception {


        String host = "localhost";
        int port = 2014;
        int choice;

        Scanner scanner = new Scanner (System.in);
        Socket socket = new Socket (host, port);


        try {
            
        	DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String clientName;
            String clientPhoneNumber;
            String serverMessage;
            String clientMessage;

            System.out.println("Please type Hello to connect to the server");
            	if((scanner.nextLine()).equals("Hello")) {
            		outStream.writeUTF("Hello");
            		outStream.flush();
            		serverMessage = inStream.readUTF();
            		System.out.println(serverMessage);
            	}

            	do {
                
            		System.out.println ("Enter the choice below:");
            		System.out.println ("Type 1 to STORE the phone number");
            		System.out.println ("Type 2 to GET the phone number");
            		System.out.println ("Type 3 to REMOVE the phone number");
            		System.out.println ("Type 4 to EXIT");
            		choice = scanner.nextInt();

            		switch (choice) {

                    case 1:
                        clientMessage = "STORE";
                        outStream.writeUTF (clientMessage);
                        System.out.println("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF (clientName);
                        System.out.println ("Phone Number:");
                        clientPhoneNumber = br.readLine();
                        outStream.writeUTF (clientPhoneNumber);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println (serverMessage);
                        break;

                    case 2:
                        clientMessage = "GET";
                        outStream.writeUTF (clientMessage);
                        System.out.println ("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF (clientName);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println (serverMessage);
                        break;

                    case 3:
                        clientMessage = "REMOVE";
                        outStream.writeUTF (clientMessage);
                        System.out.println ("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF (clientName);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println (serverMessage);
                        break;

                    case 4:
                        break;
                }
            }
            while (choice != 4);

                inStream.close();
                outStream.close();
        }
        catch(Exception e) {
            System.out.println (e);
        }

        socket.close();
        scanner.close();
    }
}
