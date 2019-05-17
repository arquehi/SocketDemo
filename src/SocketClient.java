import java.io.*;
import java.net.Socket;

public class SocketClient {
    public static final String host = "localhost";//服务器地址
    public static final int port = 10920;//服务器端口号

    public static void main(String[] args) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 与服务端建立连接
            socket = new Socket(host, port);
            // 建立连接后获得输出流
            outputStream = socket.getOutputStream();
            String message = "这是客户端发来的消息!";
            //首先需要计算得知消息的长度
            byte[] sendBytes = message.getBytes("GB2312");
            //然后将消息再次发送出去
            outputStream.write(sendBytes);
            socket.shutdownOutput();

            inputStream = socket.getInputStream();
            byte[] backByte = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = inputStream.read(backByte);
            while (len != -1)
            {
                sb.append(new String(backByte,0,len,"GB2312"));
                len = inputStream.read(backByte);
            }
            System.out.println("来自服务端的消息：" + sb);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try{
                inputStream.close();
                outputStream.close();
                socket.close();
            }
            catch (Exception ex2){
                ex2.printStackTrace();
            }
        }
    }
}
