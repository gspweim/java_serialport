package hello_console;

import gnu.io.CommPort; //rxtx java library
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class IOClass {
    public IOClass()
    {
        super();
    }
	
    static CommPort commPort;
    static SerialPort serialPort;
    static InputStream in;
    static OutputStream out;
    void connect () throws Exception
    {
	String portName = "/dev/ttyUSB0";
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }

    String send(String data){
	final PrintStream printStream = new PrintStream(out);
	printStream.print(data + "\r\n");
	printStream.close();
    	//out.write(data);
	return(read());
    }

    String read(){
	String sReturn = "";
	byte[] buffer = new byte[1024];
	int len = -1;
	try
	{
		while ( ( len = in.read(buffer)) > -1 )
		{

		    String s = new String(buffer,0,len);
		    System.out.print(s);
		    System.out.print(" LEN " + len);
		    sReturn = s;
		    break;
		}
	}
	catch ( IOException e )
	{
		e.printStackTrace();
	}     	
	return sReturn;
    }  
 /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
}
