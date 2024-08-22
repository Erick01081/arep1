package edu.escuelaing.arep.ASE.app;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.*;
import org.json.JSONObject;





public class SimpleWebServer {
    private static final int PORT = 8080;
    public static final String WEB_ROOT = "src/main/resources";

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];

            if (method.equals("GET")) {
                handleGetRequest(fileRequested, out, dataOut);
            } else if (method.equals("POST")) {
                System.out.println("post");
                handlePostRequest(fileRequested, in, out);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePostRequest(String fileRequested, BufferedReader in, PrintWriter out) throws IOException {
        // Leer headers y extraer Content-Length
        System.out.println("Prueba1");
        int contentLength = 0;
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }
        System.out.println("Prueba2");

        // Leer el cuerpo del POST según Content-Length
        char[] buffer = new char[contentLength];
        int bytesRead = in.read(buffer, 0, contentLength);

        if (bytesRead != contentLength) {
            System.out.println("Error: el contenido leído no coincide con Content-Length");
            out.println("HTTP/1.1 400 Bad Request");
            out.println("Content-Type: text/plain");
            out.println();
            out.println("Error: Content-Length mismatch");
            out.flush();
            return;
        }

        String body = new String(buffer);
        System.out.println("Prueba3");

        // Extraer el nombre del archivo usando org.json
        String fileName = "";
        String fileContent = "";
        try {
            JSONObject json = new JSONObject(body);
            fileName = json.getString("name");
            fileContent = json.getString("content");  // Asumiendo que el contenido del archivo está bajo "content"
            System.out.println("Nombre del archivo extraído: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "default.txt";
            fileContent = body;  // Si no se encuentra un JSON válido, escribir todo el cuerpo del POST
        }
        System.out.println("Prueba4");

        // Escribir el contenido del cuerpo en el archivo
        try (FileWriter fileWriter = new FileWriter(new File(SimpleWebServer.WEB_ROOT, fileName))) {
            fileWriter.write(fileContent);
        }
        System.out.println("Prueba5");

        // Responder al cliente
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("File created: " + fileName);
        out.flush();
        System.out.println("Prueba6");
    }




    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(SimpleWebServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);
        String fileReadable = file.toString();
        if (file.exists()) {


            if (fileReadable.endsWith(".png") || fileReadable.endsWith(".jpg") || fileReadable.endsWith(".jpeg")) {
                byte[] imageData = getImageContent(fileReadable);
                String base64Image = Base64.getEncoder().encodeToString(imageData);

                String htmlResponse = "<!DOCTYPE html>\r\n"
                        + "<html>\r\n"
                        + "    <head>\r\n"
                        + "        <title>Imagen</title>\r\n"
                        + "    </head>\r\n"
                        + "    <body>\r\n"
                        + "         <center><img src=\"data:image/jpeg;base64," + base64Image + "\" alt=\"image\"></center>\r\n"
                        + "    </body>\r\n"
                        + "</html>";

                // Send HTML response with embedded base64 image
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println();
                out.println(htmlResponse);
            }else {

                byte[] fileData = readFileData(file, fileLength);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            System.out.printf(fileReadable);
            out.println();
            out.flush();
            out.println("<html><body><h1>File is Not Found</h1></body></html>");
            out.flush();
        }
    }
    private static byte[] getImageContent(String file) throws IOException {
        Path filePath = Paths.get(file);
        return Files.readAllBytes(filePath);
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) fileIn.close();
        }
        return fileData;
    }
}