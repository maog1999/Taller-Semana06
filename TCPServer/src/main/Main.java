package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import processing.core.PApplet;

public class Main extends PApplet {
	

	//Globales
	int xBolita = 250;
	int yBolita = 250;
	int r=210,g=44,b=10;
	String za;
	
	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	// 1
	public void settings() {
		size(500, 500);
	}

	// 1
	public void setup() {

		// Ejecutar en segundo
		new Thread(() -> {
			try {
				ServerSocket server = new ServerSocket(5000);
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Cliente esta conectado");

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// Hacer que el objeto is tenga la capacidad de leer Strings completos
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				while (true) {
					// Esperando mensaje
					System.out.println("Esperando mensaje...");
					String mensajeRecibido = breader.readLine(); //Indicacion
					
					System.out.println(mensajeRecibido);
					
					Gson gson = new Gson();
					
					
					//Deserializacion
					Coordenada info = gson.fromJson(mensajeRecibido, Coordenada.class);
					
					String indicacion = info.getA();
					
					switch(indicacion) {
						case "up":
							System.out.println("entre");

							yBolita -= 20;
							break;
						
						case "right":
							xBolita += 20;
							break;
							
						case "left":
							xBolita -= 20;
							break;
							
						case "down":
							yBolita += 20;
							break;
							
						case "color":
							r = (int)(Math.random()*255 +1);
							g = (int)(Math.random()*255 +1);
							b = (int)(Math.random()*255 +1);

							break;
							
					}
					
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

	// Inifito
	public void draw() {
		background(155, 155, 155);
		fill(r, g, b);
		ellipse(xBolita, yBolita, 50, 50);
	}

}
