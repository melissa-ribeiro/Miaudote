
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ConexaoBanco {

    public static JdbcTemplate obterTemplate() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/miaudote");
        dataSource.setUsername("root");
        dataSource.setPassword("RFM80021042");

        return new JdbcTemplate(dataSource);
    }
}

