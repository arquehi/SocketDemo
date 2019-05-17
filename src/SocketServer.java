import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static final int Port = 10920;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            System.out.println("服务器启动...\n");

            server = new ServerSocket(Port);

            // server将一直等待连接的到来
            System.out.println("Socket Server 监听中!");

            socket = server.accept();

            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
            BufferedInputStream in = new BufferedInputStream(inputStream);
            // 取得文件名
            in.read(bytes);
            String GetMessage = new String(bytes, 0,bytes.length,"GB2312");
            GetMessage = GetMessage.replaceAll("\\u0000", "");
            System.out.println("来自客户端的消息:" + GetMessage);
            socket.shutdownInput();

            String sendMessage = "";
            String filePath = "/Users/arquehi/Documents/Code/Java/SocketDemo/src/report";
            FileInputStream fin = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            String message = "";
            while ((message = buffReader.readLine()) != null) {
                sendMessage += message;
            }
            buffReader.close();
            sendMessage = sendMessage.trim();

            outputStream = socket.getOutputStream();
            byte[] sendByte = sendMessage.getBytes("GB2312");
            outputStream.write(sendByte);
            socket.shutdownOutput();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();
                server.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
