// 1 parte Pacotes
package petstore;

// 2 parte Bibliotecas


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

// 3 parte Classe
public class Pet {
    // 3.1 Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // Endereço da entidade Pet

    // 3.2 Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir => Create => Post
    @Test // Identifica o método ou função como um teste para TestNG
    public void incluirPet() throws IOException{
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então (em Ingles = Given - Whwn - Then)

        given()
               .contentType("application/json") // comum em API(novas) REST - API(antigas) "text/xml
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log()
                .all()
                .statusCode(200)
        ;


    }

}
