
/***
 * References taken from slides in class for structure and design of the client and servers
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;

public class Client extends JFrame implements ActionListener {

    // Field for height input
    private JTextField heightTF = new JTextField();

    // Field for weight input
    private JTextField weightTF = new JTextField();

    // Field for data displaying
    private JTextArea jta = new JTextArea();

    // input and output streams for the server
    private DataOutputStream outputToServer;
    private DataInputStream inFromServer;

    public static void main(String[] args) 
    {
        new Client();
    }

    public Client ()
    {
        // Container holding the fields and their labels
        JPanel fieldContainer = new JPanel();
        //Labels and confirmantion button for the data
        JLabel weightLabel = new JLabel("Enter Weight: ");
        JLabel heightLabel = new JLabel("Enter Height: ");
        JButton sendButton = new JButton("Send to Server");

        // Layout and visualization controllers for the data
        weightLabel.setHorizontalAlignment(JLabel.RIGHT);
        heightLabel.setHorizontalAlignment(JLabel.RIGHT);

        fieldContainer.setLayout(new GridLayout(3,2));
        fieldContainer.add(heightLabel);
        fieldContainer.add(heightTF);
        fieldContainer.add(weightLabel);
        fieldContainer.add(weightTF);
        fieldContainer.add(sendButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(fieldContainer, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
        
        // listens for the button to be pressed
        sendButton.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);

        try 
        {
            // opens socket attempting to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // if connection is established, instantiate input and output streams
            inFromServer = new DataInputStream(socket.getInputStream());
            outputToServer = new DataOutputStream(socket.getOutputStream());
        } 
        catch (IOException ex)
        {
            System.err.println(ex + " \n");
        }
    }

    public void actionPerformed(ActionEvent e) 
    {

        try
        {
            // parse height and weight values to send to the server
            double height = Double.parseDouble(heightTF.getText().trim());
            double weight = Double.parseDouble(weightTF.getText().trim());

            // write values to the server
            outputToServer.writeDouble(height);
            outputToServer.writeDouble(weight);

            // recieve calculated value from the server
            double bmi = inFromServer.readDouble();

            // print to viewable display on the client
            jta.append("Height is : " + height + "\n");
            jta.append("Weight is : " + weight + "\n");

            jta.append("BMI recieved from the server is :" + bmi + "\n");
        }
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
    }
}  