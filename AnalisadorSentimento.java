package src;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AnalisadorSentimento {
    public static void main(String[] args) throws Exception {
        String chave = "EwuVSd4B0eTlLAccktAoMiQpTZCUQ6JTtVMiJR8hblVfcPYnWXPHJQQJ99CFACZoyfiXJ3w3AAAaACOGvKxT";
        String pontoExtremidade = "https://puc-exercicio4-ia.cognitiveservices.azure.com/";
        String urlCompleta = pontoExtremidade + "language/:analyze-text?api-version=2022-05-01";

        String nomeJogo = "Elden Ring";
        String textoCritica = "O mundo aberto de " + nomeJogo + " extremamente divertido, desafiante, nao menospreza a inteligencia do jogador e uma infinidade de coisas para fazer.";

        String corpoRequisicao = "{\"kind\": \"SentimentAnalysis\", \"analysisInput\": {\"documents\": [{\"id\": \"1\", \"language\": \"pt\", \"text\": \"" + textoCritica + "\"}]}}";

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .header("Ocp-Apim-Subscription-Key", chave)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpoRequisicao))
                .build();

        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

        System.out.println(resposta.statusCode());
        System.out.println(resposta.body());
    }
}