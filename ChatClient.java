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
 //line���ڶ�ȡ�����е�һ���ַ���������printwriter����д��
String line = null;

 private static final long serialVersionUID = 1L;
 public ChatClient(String title){
 //��ʼ�����ڽ���
super(title);
 this.setBounds(100, 100, 450, 370);
 this.setVisible(true);
 this.setLayout(new BorderLayout());
 this.add(displayFrame,BorderLayout.CENTER);
 displayFrame.setEditable(false);
 this.add(inputFrame,BorderLayout.SOUTH);
 //����������һ�����س��ļ�����
inputFrame.addActionListener(new MyListener());
 //���ӹرմ��ڵļ�����
this.addWindowListener(new WindowAdapter(){
 public void windowClosing(WindowEvent e) {
 try {
 //s.shutdownOutput();//�ͻ���socketʹ��shutdownOutput() ���� close() 
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
 //���ӵ�������
connect();
 }
 public static void main(String[] args) {
 ChatClient chatClient = new ChatClient("�ͻ��˴���");
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
 System.out.println(str);//����������ڵ��ԣ��ڿ���̨��ʾ��Ϣ����û��ִ�С�
}
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 }
 }
 