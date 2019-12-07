/***
 * References taken from slides in class for structure and design of the client and servers
 */




import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SingleClientServer extends JFrame {

    //Text area for diplaying human readable values
    private JTextArea jta = new JTextArea();

    public static void main(String[] args)
    {
        new SingleClientServer();
    }

    public SingleClientServer()
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

            //awaiting a client to connect and assinging them a socket
            Socket socket = serverSocket.accept();

            // once connnection is established, creates data input and output streams
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
                jta.append(socket.getLocalAddress().toString() + " Height received was: " + height + "\n");
                jta.append(socket.getLocalAddress().toString() + " Weight received was: " + weight + "\n");

                jta.append(socket.getLocalAddress().toString() + " BMI is: " + bmi + "\n");
            }
        } catch (IOException e) 
        {
            System.err.println(e);
        }
    }
}
