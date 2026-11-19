import org.springframework.jdbc.core.JdbcTemplate;

public class TesteJdbcTemplate {
    static void main(String[] args) {
        JdbcTemplate template = ConexaoBanco.obterTemplate();

        Integer resultado = template.queryForObject(
                "SELECT 1", Integer.class
        );

        System.out.println("Conexão funcionando: " + resultado);
    }
}
