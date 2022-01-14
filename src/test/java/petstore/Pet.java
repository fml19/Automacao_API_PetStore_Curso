// 1 parte Pacotes
package petstore;

// 2 parte Bibliotecas


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 parte Classe
public class Pet {
    // 3.1 Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // Endereço da entidade Pet

    // 3.2 Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir => Create => Post
    @Test(priority = 1) // Identifica o método ou função como um teste para TestNG
    public void incluirPet() throws IOException{
        String jsonBody = lerJson("db/pet1.json");

        // REST-assured
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
                .body("name", is("Max")) //validar além dos números // incluir biblioteca is
                .body("status", is("available")) //validar
                .body("category.name", is("Dog")) //validar
                .body("tags.name", contains("sta")) // validar palavra dentro de outra categoria
        ;


    }
    // Consultar =>  => GET
    @Test(priority = 2)
    public void consultarPet() {
            String petId = "1201202236";

        // REST-assured
        // Sintaxe Gherkin
        // Dado - Quando - Então (em Ingles = Given - Whwn - Then)

            String token =
            given()
                .contentType("application/json")
                .log().all()
            .when()
                .get(uri + "/" + petId)
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Max"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))
            .extract()
                    .path("category.name")
           ;
        // sout enter = escrever no final do terminal o token
        System.out.println("O token é " + token);
    }
    // Alterar pet Upgrade = Put
    @Test(priority = 3)
    public void alterarPet() throws IOException {

        String jsonBody = lerJson("db/pet2.json");

        // REST-assured
        // Sintaxe Gherkin
        // Given indica o contexto, pré requisito da requisição
        // Dado - Quando - Então (em Ingles = Given (incluir) - When - Then(resposta))


        given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
        .when()
                        .put(uri)
        .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is ("Max"))
                        .body("status", is ("sold"))
        ;

    }
    // ExcluirPet = delete
    @Test(priority = 4)
    public void excluirPet(){
        String  petId = "1201202236";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is (200))
                .body("type", is ("unknown"))
                .body("message", is (petId))
        ;
    }
}
