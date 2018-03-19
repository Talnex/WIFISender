package com.example.talnex.socketwifi.utils;

/**
 * Created by talnex on 2018/3/16.
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SockertManager {

    public static void SendFiles(List<File> files, String  ip,int port){

    }

    public static void SendFile(File file,String ip,int port) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket(20002);
        InputStream inputStream=new FileInputStream(file);
        byte[] buf=new byte[1024];
        int c;
        while ((c=inputStream.read(buf))!=-1){
            DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),port);
            datagramSocket.send(datagramPacket);
            System.out.println("发送了一个包");
        }
        inputStream.close();
        System.out.println("发送完毕");
        datagramSocket.close();
    }

    public static void SendString(String string,String ip,int port) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket(20000);
        byte[] buf=string.getBytes();
        DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }

    public static void SendString(List<String> strings,String  ip,int port) throws IOException, InterruptedException {
        for (int i = 0; i!=strings.size();i++){
            SendString(strings.get(i),ip,port);
            Thread.sleep(1000);
        }
    }

    public static String ReciveString(int port) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket(port);
        byte[] buf=new byte[1024];
        DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length);
        datagramSocket.receive(datagramPacket);
        String data=new String(datagramPacket.getData(),0,datagramPacket.getLength());
        return data;
    }

    public static ArrayList<String> ReciveStrings(int port) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket(port);
        ArrayList<String> data=new ArrayList<>();
        String string;
        while (true){
            byte[] buf=new byte[1024];
            DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length);
            datagramSocket.receive(datagramPacket);
            string=new String(datagramPacket.getData(),0,datagramPacket.getLength());
            if (string.equals("exit"))break;
            else data.add(string);
        }
        return data;
    }

    public static void ReciveFile(File file,int size,int port) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(port);
        byte[] buf=new byte[1024];
        OutputStream out=new FileOutputStream(file);
        for (int i=0;i<size/1024;i++) {
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(datagramPacket);
            out.write(datagramPacket.getData());
            System.out.println("接收到一个包");
        }
        System.out.println("接收完毕");
        out.close();
        datagramSocket.close();

    }

    public static void ReciveFiles(int port){

    }

    public static int getbytelength(byte[] data) {
        int i = 0;
        for (; i < data.length; i++) {
            if (data[i] == '\0')
                break;
        }
        return i;
    }
}
