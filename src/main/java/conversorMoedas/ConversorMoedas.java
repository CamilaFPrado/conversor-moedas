package main.java.conversorMoedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMoedas {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conversor de Moedas ===");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - USD para BRL");
        System.out.println("2 - EUR para USD");
        System.out.println("3 - GBP para BRL");
        System.out.println("4 - USD para JPY");
        System.out.println("5 - BRL para USD");
        System.out.println("6 - EUR para BRL");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();

        String base = "", destino = "";

        switch (opcao) {
            case 1 -> { base = "USD"; destino = "BRL"; }
            case 2 -> { base = "EUR"; destino = "USD"; }
            case 3 -> { base = "GBP"; destino = "BRL"; }
            case 4 -> { base = "USD"; destino = "JPY"; }
            case 5 -> { base = "BRL"; destino = "USD"; }
            case 6 -> { base = "EUR"; destino = "BRL"; }
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        System.out.print("Digite o valor que deseja converter: ");
        double valor = scanner.nextDouble();

        String apiKey = "c5bee962a404c3670be7737b";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + base;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject rates = json.getAsJsonObject("conversion_rates");

        if (rates.has(destino)) {
            double taxa = rates.get(destino).getAsDouble();
            double convertido = valor * taxa;
            System.out.printf("Valor convertido: %.2f %s%n", convertido, destino);
        } else {
            System.out.println("Moeda de destino não encontrada.");
        }

        scanner.close();
    }
}
