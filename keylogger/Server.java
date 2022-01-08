import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {


        try{
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,16,2,4,44100,false);
            DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            if(!AudioSystem.isLineSupported(dataInfo)){
                System.out.println("Not supported");
            }

            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);

            targetLine.open();

            targetLine.start();

            Thread audioRecorderThread = new Thread(){
                @Override public void run(){
                    AudioInputStream recordingStream= new AudioInputStream(targetLine);

                    File outputFile = new File("record.wav");
                    try{
                        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                    System.out.println("Stopped recording");
                }
            };
            audioRecorderThread.start();
            ServerSocket serverSocket = new ServerSocket(2000);
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            FileWriter filewriter = new FileWriter("ServerOutputFile.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            while (socket.isConnected()){
                String str = objectInputStream.readUTF();
                System.out.println("LOGGED : " + str );
                bufferedWriter.write(str + "\n");
                bufferedWriter.flush();
            }
            targetLine.stop();
            targetLine.close();

        }catch(Exception e){
            System.out.println(e);
        }



        System.out.println("Connection closed");
    }
}
