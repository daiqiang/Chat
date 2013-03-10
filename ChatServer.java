import java.net.*;
 import java.util.*;
 import java.io.*;
 
public class ChatServer {
 private ArrayList<Client> clients = new ArrayList<Client>();

 public static void main(String[] args) {
 new ChatServer().start();
 }

 private void start(){
 ServerSocket ss=null;
 int i=0;
 try {
 ss = new ServerSocket(8888);
 //started = true;
 while(true){
 i++; 
Client c = new Client(ss.accept());
 System.out.println(i+" client logined");//试用输出
new Thread(c).start();
 clients.add(c);
 }
 } catch (IOException e1) {
 e1.printStackTrace();
 }
 }
 
private class Client implements Runnable{
 private Socket s = null;
 private boolean beConnect = false;
 private String line=null;
 private BufferedReader br = null;
 private PrintWriter pw = null;

 Client (Socket s){
 this.s=s;
 beConnect = true;
 try {
 br = new BufferedReader(new InputStreamReader(s.getInputStream()));
 pw = new PrintWriter(s.getOutputStream());
 } catch (IOException e) {
 e.printStackTrace();
 }
 }

 public void run() {
 while(beConnect){
 try {
 line=br.readLine();
 if(null!=line){
 System.out.println(line);//输出到控制台，调试使用。
for(int i=0;i<clients.size();i++)
 {
 Client c = clients.get(i);
 c.send(line);
 }
 }else{
 beConnect = false;
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 }

 private void send(String str){
 pw.println(str);
 }
 }
 }