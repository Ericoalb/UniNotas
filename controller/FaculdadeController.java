package Unicotas;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class FaculdadeController implements HttpHandler {
    private FaculdadeService faculdadeService;

    public FaculdadeController(FaculdadeService faculdadeService)
    {
        this.faculdadeService = faculdadeService;
    }

    // método obrigatório que recebe TUDO que vem do Postman
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String method = exchange.getRequestMethod();
        try
        {
            if(method.equals("POST"))
            {
                // 1. Lê o texto (JSON) que você enviou no Postman
                String body = new String(exchange.getRequestBody().readAllBytes());


                // 2. Truque simples para pegar os valores cortando as aspas
                // ATENÇÃO: No Postman, mande exatamente assim: {"name":"Teste","cnpj":"123","id": 0}
                body = body.replace("{", "").replace("}", "").replace("\"", "").replace(" ", "").replace("\n", "");
                String[] atributos = body.split(",");
                String nome = atributos[0].split(":")[1].trim();
                String cnpj = atributos[1].split(":")[1].trim();
                String idTexto = atributos[2].split(":")[1].trim(); // Pega o ID que você enviou

                int id = Integer.parseInt(idTexto);

                // 4. Cria a faculdade com o SEU ID e salva
                FaculdadeEntity faculdade = new FaculdadeEntity(nome, cnpj, id);
                faculdadeService.cadastrarFaculdadede(faculdade);
                enviarResposta(exchange, 201, "Faculdade salva com ID " + id + "!");
            }
            else if (method.equals("GET"))
            {
                // 1. Pega a lista do Service
                ArrayList<FaculdadeEntity> listaFaculdade = faculdadeService.listarFaculdade();

                // 2. Monta um texto simples com os dados
                String resposta = "[";
                for (int i = 0; i < listaFaculdade.size(); i++)
                {
                    FaculdadeEntity faculdade = listaFaculdade.get(i);
                    resposta += "{\"id\":" + faculdade.getId() + ", \"name\":\"" + faculdade.getName() + "\", \"cnpj\":\"" + faculdade.getCnpj() + "\"}";
                    if (i < listaFaculdade.size() - 1) resposta += ",";
                }
                resposta += "]";
                enviarResposta(exchange, 200, resposta);
            }
            else if (method.equals("PUT"))
            {
                // Lê os dados do Postman no mesmo formato do POST
                String body = new String(exchange.getRequestBody().readAllBytes());
                body = body.replace("{", "").replace("}", "").replace("\"", "").replace(" ", "").replace("\n", "").replace("\r", "");

                String[] atributos = body.split(",");
                String nome = atributos[0].split(":")[1].trim();
                String cnpj = atributos[1].split(":")[1].trim();
                int id = Integer.parseInt(atributos[2].split(":")[1].trim());

                // Busca a faculdade existente na memória pelo ID e atualiza os dados
                FaculdadeEntity faculdade = faculdadeService.buscarFaculdadeId(id);
                faculdadeService.atualizarFaculdade(faculdade, nome, cnpj);

                enviarResposta(exchange, 200, "Faculdade ID " + id + " atualizada com sucesso!");
            }
            else if (method.equals("DELETE"))
            {
                // Para o DELETE, você envia apenas o ID no Postman. Ex: {"id": 0}
                String body = new String(exchange.getRequestBody().readAllBytes());
                body = body.replace("{", "").replace("}", "").replace("\"", "").replace(" ", "").replace("\n", "").replace("\r", "");

                int id = Integer.parseInt(body.split(":")[1].trim());

                // Busca a faculdade existente pelo ID e remove da lista
                FaculdadeEntity faculdade = faculdadeService.buscarFaculdadeId(id);
                faculdadeService.removerFaculdade(faculdade);

                enviarResposta(exchange, 200, "Faculdade ID " + id + " excluida com sucesso!");
            }
        }
        catch (Exception e)
        {
            // Se der erro ao ler ou cortar o texto, ele cai aqui e envia a mensagem para o Postman
            e.printStackTrace();
            enviarResposta(exchange, 400, "Erro ao processar a requisicao. Verifique o formato do JSON enviado no Postman.");
        }



    }


    // Método curtinho para devolver a resposta ao Postman
    private void enviarResposta(HttpExchange exchange, int code, String texto) throws IOException
    {
        exchange.sendResponseHeaders(code, texto.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(texto.getBytes());
        os.close();
    }
}