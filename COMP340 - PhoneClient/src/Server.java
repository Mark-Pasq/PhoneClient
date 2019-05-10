
import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Server {

    public static void main(String[] args) throws Exception{

        try {
            int port = 2014;
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket (port);
            int counter = 0;
            System.out.println ("The Server has started running .. ");

            do {
                counter++;

                //The client connection request is accepted
                Socket serverClient = server.accept();
				System.out.println ("PhoneClient no: " + counter + " is started..!");

                //The request is sent to a separate thread
                ServerClientThread thread = new ServerClientThread(serverClient, counter);
                thread.start();
            }
            
            while (true);
        }

        catch(Exception e) {
            System.out.println(e);
            
        }
    }
}
class ServerClientThread extends Thread {

    private Socket serverClient;
    private int clientNum;

    ServerClientThread (Socket inSocket, int counter) {
        serverClient = inSocket;
        clientNum = counter;
    }
    
	public void run() {

        try {
            DataInputStream inStream = new DataInputStream (serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream (serverClient.getOutputStream());
            String clientName;
            String clientPhoneNumber;
            String clientMessage = "";

            //  List<List<String>> al = new ArrayList<>();

            HashMap< String, String > al = new HashMap< >();

            while (!clientMessage.equals ("4")) {

            	clientMessage = inStream.readUTF();

//                if(clientMessage.equals("HELLO")) {
//                      outStream.writeUTF(100 + "OK");
//                      outStream.writeUTF("msg" + "Oke");
//                  }

                if (clientMessage.equals ("STORE")) {
                    clientName = inStream.readUTF();
                    clientPhoneNumber = inStream.readUTF();

                    al.put (clientName, clientPhoneNumber);

                    System.out.println ("Hashmap stored : " + al);

                    outStream.writeUTF ("100" + " OK");
                }

                if (clientMessage.equals("GET")) {

                    //getting client name
                    clientName = inStream.readUTF();

//                    String index = al.get (clientName).toString();
//                    al.indexOf (clientName, clientPhoneNumber);
//                    System.out.println (clientName + " " + al.get (clientName).toString()
//                    					+ " " + al.get(0) + " : " + al.size() + " ");
//
                    if (al.containsKey (clientName)) {
                        outStream.writeUTF ("200" + " " + al.get (clientName));
                    }

                    else
                    {
                        outStream.writeUTF ("300" + " NOT FOUND");
                    }
//                    al.get(index);
                }

                if (clientMessage.equals ("REMOVE")) {

                    clientName = inStream.readUTF();

                    if (al.containsKey (clientName)) {
                        al.remove (clientName);

                        System.out.println (clientName + " " + al.get (clientName).toString());

                        outStream.writeUTF("100" + " OK");
                    }

                    else {
                        outStream.writeUTF ("300" + " NOT FOUND");
                    }
                }

//                else {
//                	outStream.writeUTF ("400" + " BAD REQUEST");
//                }
            

                outStream.flush();
                inStream.close();
                outStream.close();
                serverClient.close();
            }
        }
            
        catch (Exception ex) {
        	System.out.println (ex);
        }

        finally {

            System.out.println("PhoneClient -" + clientNum + " exit..!! ");
        }
    } 
}
