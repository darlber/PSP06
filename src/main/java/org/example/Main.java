package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static final String LOG_FILE = "registro_actividad.log";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean valido = false;
        boolean ficheroValido = false;
        String login;
        String nombreFichero;

        logActividad("INICIO", "Programa iniciado");

        System.out.println("ACTIVIDAD 6.1 - VALIDACIÓN DE LOGIN");
        System.out.println("------------------------------------");
        System.out.println("Requisitos del login:");
        System.out.println("- 8 caracteres exactos");
        System.out.println("- Solo letras minúsculas (a-z)");
        System.out.println("------------------------------------");

        do {
            System.out.print("Introduzca su nombre de usuario: ");
            login = scanner.nextLine();

            if (login.matches("^[a-z]{8}$")) {
                System.out.println("\nLogin válido. Bienvenido, " + login);
                valido = true;
                logActividad("LOGIN", "Login exitoso: " + login);
            } else {
                System.out.println("\nERROR: El login no cumple los requisitos");
                System.out.println("Por favor, inténtelo de nuevo.\n");
                logActividad("LOGIN", "Login fallido: " + login);
            }
        } while (!valido);


        // Parte 2: Validación del nombre de fichero (solo después de login válido)
        System.out.println("\n\nVALIDACIÓN DE NOMBRE DE FICHERO");
        System.out.println("-------------------------------");
        System.out.println("Requisitos del nombre de fichero:");
        System.out.println("- Máximo 8 caracteres para el nombre");
        System.out.println("- Extensión exacta de 3 caracteres");
        System.out.println("- Formato: nombre.ext");
        System.out.println("-------------------------------");


        do {
            System.out.print("Introduzca el nombre del fichero: ");
            nombreFichero = scanner.nextLine();

            if (nombreFichero.matches("^[a-zA-Z0-9]{1,8}\\.[a-zA-Z0-9]{3}$")) {
                System.out.println("\n Nombre de fichero valido: " + nombreFichero);
                ficheroValido = true;
                logActividad("FICHERO", "Nombre de fichero valido: " + nombreFichero);
            } else {
                System.out.println("\nERROR: El nombre no cumple los requisitos");
                System.out.println("Ejemplo válido: archivo.txt");
                System.out.println("Por favor, inténtelo de nuevo.\n");
                logActividad("FICHERO", "Nombre de fichero invalido: " + nombreFichero);
            }
        } while (!ficheroValido);
        leerFichero(nombreFichero);
        scanner.close();
        logActividad("FIN", "Programa finalizado");
    }

    public static void leerFichero(String nombreFichero) {
        System.out.println("\nContenido del fichero " + nombreFichero + ":");
        System.out.println("----------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("----------------------------------------");
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.err.println("\nError al leer el fichero: " + e.getMessage());
            System.err.println("Asegúrese de que el fichero existe y tiene permisos de lectura.");
        }
    }

    public static void logActividad(String tipo, String mensaje) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            String timestamp = dateFormat.format(new Date());
            String registro = String.format("[%s] [%s] %s", timestamp, tipo, mensaje);

            out.println(registro);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de registro: " + e.getMessage());
        }
    }
}
