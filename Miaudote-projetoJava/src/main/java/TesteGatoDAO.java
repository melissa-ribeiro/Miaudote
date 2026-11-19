
import java.util.List;

public class TesteGatoDAO {

    public static void main(String[] args) {

        System.out.println("========== INÍCIO DOS TESTES DO MIAUDOTE ==========");

        GatoDAO dao = new GatoDAO(ConexaoBanco.obterTemplate());

        // 1. CADASTRO DE GATO FILHOTE
        System.out.println("\n========== 1. CADASTRO DE FILHOTE ==========");

        GatoFilhote filhote = new GatoFilhote(
                "Billie",
                "macho",
                3,
                "Bagunceiro e curioso",
                true,
                "Precisa de acompanhamento veterinário",
                "Pelagem laranja e olhos azuis",
                true,
                true
        );

        dao.save(filhote);

        System.out.println("Filhote cadastrado!");
        System.out.println(filhote);
        System.out.println("ID gerado: " + filhote.getId());


        // 2. CADASTRO DE GATO ADULTO
        System.out.println("\n========== 2. CADASTRO DE GATO ADULTO ==========");

        GatoAdulto adulto = new GatoAdulto(
                "Lana",
                "fêmea",
                12,
                "Carinhosa e apegada",
                false,
                null,
                "Pelagem branca e olhos verdes",
                false,
                true,
                "Convive bem com outros animais"
        );

        dao.save(adulto);

        System.out.println("Gato adulto cadastrado!");
        System.out.println(adulto);
        System.out.println("ID gerado: " + adulto.getId());


        // 3. BUSCA POR NOME E ID
        System.out.println("\n========== 3. BUSCAR POR NOME E ID ==========");

        Gato encontrado = dao.buscarNomeId(
                "Billie",
                filhote.getId()
        );

        if (encontrado != null) {
            System.out.println("Gatinho encontrado!");
            System.out.println(encontrado);
        } else {
            System.out.println("Gatinho não encontrado :(");
        }


        // 4. BUSCAR TODOS OS GATOS
        System.out.println("\n========== 4. TODOS OS GATOS ==========");

        List<Gato> todos = dao.buscarTodos();

        for (int i = 0; i < todos.size(); i++) {
            System.out.println(todos);
        }

        System.out.println("Total na lista: " + todos.size());


        // 5. BUSCAR FILHOTES
        System.out.println("\n========== 5. GATOS FILHOTES ==========");

        List<GatoFilhote> filhotes = dao.buscarGatosFilhotes();

        for (GatoFilhote gato : filhotes) {
            System.out.println(gato);
        }

        System.out.println("Total de filhotes na lista: " + filhotes.size());


        // 6. BUSCAR ADULTOS
        System.out.println("\n========== 6. GATOS ADULTOS ==========");

        List<GatoAdulto> adultos = dao.buscarGatosAdultos();

        for (GatoAdulto gato : adultos) {
            System.out.println(gato);
        }

        System.out.println("Total de adultos na lista: " + adultos.size());


        // 7. BUSCAR GATOS QUE PRECISAM DE CUIDADOS ESPECIAIS
        System.out.println("\n========== 7. CUIDADOS ESPECIAIS ==========");

        List<Gato> especiais = dao.buscarGatinhosEspeciais();

        for (Gato gato : especiais) {
            System.out.println(gato);
        }

        System.out.println("Total: " + especiais.size());


        // 8. BUSCAR GATOS VACINADOS
        System.out.println("\n========== 8. GATOS VACINADOS ==========");

        List<Gato> vacinados = dao.buscarGatinhosVacinados();

        for (Gato gato : vacinados) {
            System.out.println(gato);
        }

        System.out.println("Total: " + vacinados.size());


        // 9. BUSCAR GATOS DISPONÍVEIS PARA ADOÇÃO
        System.out.println("\n========== 9. GATOS DISPONÍVEIS ==========");

        List<Gato> disponiveis = dao.buscarGatinhosDisponiveis();

        for (Gato gato : disponiveis) {
            System.out.println(gato);
        }

        System.out.println("Total: " + disponiveis.size());


        // 10. BUSCAR GATOS ADOTADOS
        System.out.println("\n========== 10. GATOS ADOTADOS ==========");

        List<Gato> adotados = dao.buscarGatinhosAdotados();

        for (Gato gato : adotados) {
            System.out.println(gato);
        }

        System.out.println("Total: " + adotados.size());


        // 11. ATUALIZAR INFORMAÇÕES
        System.out.println("\n========== 11. ATUALIZAÇÃO ==========");

        filhote.setPersonalidade("Brincalhão e carinhoso");
        filhote.setIdadeMeses(4);

        boolean atualizado = dao.updateInfoGatinhos(filhote);

        if (atualizado) {
            System.out.println("Gato atualizado com sucesso!");
            System.out.println(filhote);
        } else {
            System.out.println("Não foi possível atualizar o gato.");
        }


        // 12. CONFERIR SE A ATUALIZAÇÃO PERSISTIU NO BANCO
        System.out.println("\n========== 12. CONFERÊNCIA NO BANCO ==========");

        GatoDAO daoConsulta = new GatoDAO(
                ConexaoBanco.obterTemplate()
        );

        Gato gatoConsultado = daoConsulta.buscarNomeId(
                "Layla",
                filhote.getId()
        );

        if (gatoConsultado != null) {
            System.out.println("Registro encontrado novamente no banco!");
            System.out.println(gatoConsultado);

            System.out.println(
                    "Personalidade atualizada? "
                            + gatoConsultado.getPersonalidade()
            );

            System.out.println(
                    "Idade atualizada? "
                            + gatoConsultado.getIdadeMeses()
            );
        } else {
            System.out.println("Registro não encontrado no banco.");
        }


        // 13. EXCLUIR O FILHOTE DE TESTE
        System.out.println("\n========== 13. EXCLUSÃO DO FILHOTE ==========");

        boolean excluidoFilhote = daoConsulta.excluirGato(
                filhote.getId()
        );

        if (excluidoFilhote) {
            System.out.println("Filhote excluído com sucesso!");
        } else {
            System.out.println("Não foi possível excluir o filhote.");
        }


        // 14. EXCLUIR O ADULTO DE TESTE
        System.out.println("\n========== 14. EXCLUSÃO DO ADULTO ==========");

        boolean excluidoAdulto = daoConsulta.excluirGato(
                adulto.getId()
        );

        if (excluidoAdulto) {
            System.out.println("Adulto excluído com sucesso!");
        } else {
            System.out.println("Não foi possível excluir o adulto.");
        }


        // 15. CONFERIR AS EXCLUSÕES NO BANCO
        System.out.println("\n========== 15. CONFERÊNCIA DAS EXCLUSÕES ==========");

        GatoDAO daoFinal = new GatoDAO(
                ConexaoBanco.obterTemplate()
        );

        Gato laylaFinal = daoFinal.buscarNomeId(
                "TesteLayla",
                filhote.getId()
        );

        Gato luluFinal = daoFinal.buscarNomeId(
                "TesteLulu",
                adulto.getId()
        );

        System.out.println(
                "Layla ainda existe? " + (laylaFinal != null)
        );

        System.out.println(
                "Lulu ainda existe? " + (luluFinal != null)
        );

        System.out.println("\n========== FIM DOS TESTES ==========");


    }
}