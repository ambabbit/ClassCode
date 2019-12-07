/***
 * References taken from slides in class for structure and design of the client and servers
 */




import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MultiClientServer extends JFrame {
    
    //Text area for diplaying human readable values
    private JTextArea jta = new JTextArea();

    public static void main(String[] args)
    {
        new MultiClientServer();
    }

    public MultiClientServer()
    {
        // Creating visualization for users to read
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);

        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try 
        {
            // opening server socket at localhost:8000 for clients to connect to
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("Server started at " + new Date() + "\n");

            while (true) 
            {  
                try {
                    //awaiting clients to connect and assinging them a socket
                    Socket socket = serverSocket.accept();
                    
                    // Creates thread for each client allowing multiples clients to connect
                    new EchoThread(socket, jta).start();
                    
                } 
                catch (IOException e) 
                {
                    System.err.println(e);
                };

            }
        } catch (IOException e) 
        {
            System.err.println(e);
        }
    }
}


class EchoThread extends Thread
{   
    // creates reference to socket and display area for each client
    private Socket socket;
    private JTextArea jta;
    public EchoThread (Socket clientSocket, JTextArea outputArea) {

        // assigns reference to client socket and output area
        this.socket = clientSocket;
        this.jta = outputArea;
    }

    @Override
    public void run() {
        
        try 
        {
            //creates data input and output streams for the client
            DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

            while (true) 
            {
                // recieves values sent over from the client
                double height = inputFromClient.readDouble();
                double weight = inputFromClient.readDouble();

                // Calculates BMI from recieved data
                double bmi = weight / (height * height);

                // Supplies calculated data to the client
                outputToClient.writeDouble(bmi);

                // Displays data in human readable format to the server display window
                jta.append(socket.getLocalPort() + " Height received was: " + height + "\n");
                jta.append(socket.getLocalPort() + " Weight received was: " + weight + "\n");

                jta.append(socket.getLocalPort() + " BMI is: " + bmi + "\n");
            }
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}