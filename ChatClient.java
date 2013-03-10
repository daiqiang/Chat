import java.awt.*;
 import java.awt.event.*;
 import java.io.*;
 import java.net.*;
 
public class ChatClient extends Frame{
 TextArea displayFrame = new TextArea();
 TextField inputFrame= new TextField();
 Socket s;
 PrintWriter pw;
 BufferedReader br = null;
 //line用于读取输入行的一行字符串，交给printwriter进行写入
String line = null;

 private static final long serialVersionUID = 1L;
 public ChatClient(String title){
 //初始化窗口界面
super(title);
 this.setBounds(100, 100, 450, 370);
 this.setVisible(true);
 this.setLayout(new BorderLayout());
 this.add(displayFrame,BorderLayout.CENTER);
 displayFrame.setEditable(false);
 this.add(inputFrame,BorderLayout.SOUTH);
 //给输入框添加一个按回车的监听器
inputFrame.addActionListener(new MyListener());
 //增加关闭窗口的监听器
this.addWindowListener(new WindowAdapter(){
 public void windowClosing(WindowEvent e) {
 try {
 //s.shutdownOutput();//客户端socket使用shutdownOutput() 代替 close() 
 s.close();
 //pw.flush();
 //pw.close();
 br.close();
 System.exit(0);
 } catch (IOException e1) {
 e1.printStackTrace();
 }
 }
 });
 //连接到服务器
connect();
 }
 public static void main(String[] args) {
 ChatClient chatClient = new ChatClient("客户端窗口");
 }
private class MyListener implements ActionListener{
 public void actionPerformed(ActionEvent e) {
 line=inputFrame.getText();
 displayFrame.setText(displayFrame.getText()+line+"\n");
 inputFrame.setText("");
 try {
 pw = new PrintWriter(s.getOutputStream());
 pw.println(line);
 pw.flush();
 } catch (IOException e1) {
 e1.printStackTrace();
 }

 }
 }
 private void connect(){
 try {
 s = new Socket("localhost",8888);
 new Thread(new RecvThread()).start();
 System.out.println("connect server");
 } catch (UnknownHostException e) {
 e.printStackTrace();
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 private class RecvThread implements Runnable{
 String str = null;
 RecvThread(){
 try {
 br=new BufferedReader(new InputStreamReader(s.getInputStream()));
 } catch (IOException e) {
 e.printStackTrace();
 }
 }

 public void run() {
 try {
 while(true)
 {
 System.out.print("xxxx");
 str=br.readLine();
 System.out.println(str);//这句数据用于调试，在控制台显示消息，但没有执行。
}
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 }
 }
 