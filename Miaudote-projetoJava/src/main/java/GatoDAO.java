import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GatoDAO {
    private final JdbcTemplate jdbcTemplate;
    private List<Gato> listaGatos;


    public GatoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        String sql = """
                SELECT g.*,
                       gf.desmamado,
                       ga.castrado,
                       ga.historicoComportamental
                FROM gato g
                LEFT JOIN gatoFilhote gf ON gf.fk_gato = g.id
                LEFT JOIN gatoAdulto ga ON ga.fk_gato = g.id
                """;

        this.listaGatos = jdbcTemplate.query(sql, (rs, rowNum) -> {

            Gato gato;

            if (rs.getObject("desmamado") != null) { // ve se o gatinho eh filhote
                // Cria GatoFilhote

                gato = new GatoFilhote(
                        rs.getString("nome"),
                        rs.getString("sexo"),
                        rs.getInt("idadeMeses"),
                        rs.getString("personalidade"),
                        rs.getBoolean("precisaCuidadosEspeciais"),
                        rs.getString("descricaoCuidados"),
                        rs.getString("descricaoAparencia"),
                        rs.getBoolean("vacinado"),
                        rs.getBoolean("desmamado")
                );

            } else if (rs.getObject("castrado") != null) { // vê se tem gato Adulto
                // Cria GatoAdulto
                gato = new GatoAdulto(
                        rs.getString("nome"),
                        rs.getString("sexo"),
                        rs.getInt("idadeMeses"),
                        rs.getString("personalidade"),
                        rs.getBoolean("precisaCuidadosEspeciais"),
                        rs.getString("descricaoCuidados"),
                        rs.getString("descricaoAparencia"),
                        rs.getBoolean("vacinado"),
                        rs.getBoolean("castrado"),
                        rs.getString("historicoComportamental")
                );

            } else {
                // Cria Gato
                gato = new Gato(
                        rs.getString("nome"),
                        rs.getString("sexo"),
                        rs.getInt("idadeMeses"),
                        rs.getString("personalidade"),
                        rs.getBoolean("precisaCuidadosEspeciais"),
                        rs.getString("descricaoCuidados"),
                        rs.getString("descricaoAparencia"),
                        rs.getBoolean("vacinado")
                );
            }

            gato.setId(rs.getInt("id"));
            gato.setAdotado(rs.getBoolean("adotado"));

            return gato;
        });
    }

    public void save(Gato gato) {

        if (gato.getId() == null) {

            String sql = "INSERT INTO gato (" +
                    "nome, sexo, idadeMeses, adotado, personalidade, " +
                    "precisaCuidadosEspeciais, descricaoCuidados, descricaoAparencia, vacinado" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder chave = new GeneratedKeyHolder();

            jdbcTemplate.update(conexao -> {
                PreparedStatement comando = conexao.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS
                );

                comando.setString(1, gato.getNome());
                comando.setString(2, gato.getSexo());
                comando.setInt(3, gato.getIdadeMeses());
                comando.setBoolean(4, gato.getAdotado());
                comando.setString(5, gato.getPersonalidade());
                comando.setBoolean(6, gato.getPrecisaCuidadosEspeciais());
                comando.setString(7, gato.getDescricaoCuidados());
                comando.setString(8, gato.getDescricaoAparencia());
                comando.setBoolean(9, gato.getVacinado());

                return comando;
            }, chave);

            Integer idGato = chave.getKey().intValue();

            gato.setId(idGato);

            if (gato instanceof GatoFilhote filhote) {

                String sqlFilhote = """
                    INSERT INTO gatoFilhote (desmamado, fk_gato)
                    VALUES (?, ?)
                    """;

                jdbcTemplate.update(
                        sqlFilhote,
                        filhote.getDesmamado(),
                        idGato
                );

                listaGatos.add(gato);

            } else if (gato instanceof GatoAdulto adulto) {

                String sqlAdulto = """
                        INSERT INTO gatoAdulto (
                            castrado, historicoComportamental, fk_gato
                        ) VALUES (?, ?, ?)
                        """;

                jdbcTemplate.update(
                        sqlAdulto,
                        adulto.getCastrado(),
                        adulto.getHistoricoComportamental(),
                        idGato
                );

                listaGatos.add(gato);
            }

        }
    }

    public Gato buscarNomeId(String nome, Integer id) {

        Gato encontrou = null;

        for (int i = 0; i  < listaGatos.size(); i++) {
            if(listaGatos.get(i).getNome().equalsIgnoreCase(nome) && listaGatos.get(i).getId().equals(id)){
                encontrou = listaGatos.get(i);
            }
        }
        return encontrou;
    }

//    public List<Gato> buscarTodos(){
//        List<Gato> todosGatinhos = new ArrayList<>();
//
//        for (int i = 0; i < listaGatos.size(); i++) {
//            if (listaGatos.get(i) instanceof GatoFilhote){
//                if (listaGatos.get(i).getAdotado() == false){
//                    if (listaGatos.get(i).getVacinado()){
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho filhote de "
//                                + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                " e já sou vacinado! Estou pronto para ser adotado ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }else {
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho filhote de "
//                                + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                ". Estou pronto para ser adotado se você me der vacina ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }
//                }
//            }
//
//            if (listaGatos.get(i) instanceof GatoFilhote){
//                if (listaGatos.get(i).getAdotado() == false){
//                    if (listaGatos.get(i).getVacinado()){
//                        if (listaGatos.get(i).getPrecisaCuidadosEspeciais()){
//                            System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho filhote que precisa de cuidados especiais." +
//                                    " Tenho " + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                    " e já sou vacinado! Estou pronto para ser adotado ♡");
//
//                            todosGatinhos.add(listaGatos.get(i));
//                        }
//                    } else {
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho filhote que precisa de cuidados especiais." +
//                                " Tenho " + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                ". Estou pronto para ser adotado se você me der vacina ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }
//                }
//            }
//
//            if (listaGatos.get(i) instanceof  GatoAdulto){
//                if (listaGatos.get(i).getAdotado() == false){
//                    if (listaGatos.get(i).getVacinado()){
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho adulto de " + listaGatos.get(i).getIdadeMeses() + " meses de idade." +
//                                " Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                " e já sou vacinado! Estou pronto para ser adotado ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }else {
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho adulto de " + listaGatos.get(i).getIdadeMeses() + " meses de idade." +
//                                " Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                ". Estou pronto para ser adotado se você me der vacina ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }
//                }
//            }
//
//            if (listaGatos.get(i) instanceof  GatoAdulto){
//                if (listaGatos.get(i).getAdotado() == false){
//                    if (listaGatos.get(i).getVacinado()){
//                        if (listaGatos.get(i).getPrecisaCuidadosEspeciais()){
//                            System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho adulto que precisa de cuidados especiais." +
//                                    " Tenho " + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                    " e já sou vacinado! Estou pronto para ser adotado ♡");
//
//                            todosGatinhos.add(listaGatos.get(i));
//                        }
//                    }else {
//                        System.out.println("Miau! Meu nome é: " + listaGatos.get(i).getNome() + ". Sou um gatinho adulto que precisa de cuidados especiais." +
//                                " Tenho " + listaGatos.get(i).getIdadeMeses() + " meses de idade. Sou bem " + listaGatos.get(i).getPersonalidade() +
//                                ". Estou pronto para ser adotado se você me der vacina ♡");
//
//                        todosGatinhos.add(listaGatos.get(i));
//                    }
//                }
//            }
//
//        }
//
//        return todosGatinhos;
//    }

    public List<Gato> buscarTodos(){
        return listaGatos;
    }

    public List<GatoFilhote> buscarGatosFilhotes(){
        List<GatoFilhote> gatosFilhotes = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i) instanceof  GatoFilhote){
                gatosFilhotes.add((GatoFilhote) listaGatos.get(i));
            }
        }

        return gatosFilhotes;
    }

    public List<GatoAdulto> buscarGatosAdultos(){
        List<GatoAdulto> gatosAdultos = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i) instanceof GatoAdulto){
                gatosAdultos.add((GatoAdulto) listaGatos.get(i));
            }
        }

        return gatosAdultos;
    }

    public List<Gato> buscarGatinhosEspeciais(){
        List<Gato> gatinhosEspeciais = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getPrecisaCuidadosEspeciais() == true){
                gatinhosEspeciais.add(listaGatos.get(i));
            }
        }

        return gatinhosEspeciais;
    }

    public List<Gato> buscarGatinhosVacinados(){
        List<Gato> gatinhosVacinados = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getVacinado() == true){
                gatinhosVacinados.add(listaGatos.get(i));
            }
        }

        return gatinhosVacinados;
    }

    public List<Gato> buscarGatinhosDisponiveis(){
        List<Gato> gatinhosDisponiveis = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getAdotado() == false){
                gatinhosDisponiveis.add(listaGatos.get(i));
            }
        }

        return gatinhosDisponiveis;
    }

    public List<Gato> buscarGatinhosAdotados(){
        List<Gato> gatinhosAdotados = new ArrayList<>();

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getAdotado()){
                gatinhosAdotados.add(listaGatos.get(i));
            }
        }

        return gatinhosAdotados;
    }

    public boolean updateInfoGatinhos(Gato gato) {

        if (gato.getId() == null) {
            return false;
        }

        String sql = """
        UPDATE gato SET
            nome = ?,
            sexo = ?,
            idadeMeses = ?,
            adotado = ?,
            personalidade = ?,
            precisaCuidadosEspeciais = ?,
            descricaoCuidados = ?,
            descricaoAparencia = ?,
            vacinado = ?
        WHERE id = ?
        """;

        int linhasAfetadas = jdbcTemplate.update(
                sql,
                gato.getNome(),
                gato.getSexo(),
                gato.getIdadeMeses(),
                gato.getAdotado(),
                gato.getPersonalidade(),
                gato.getPrecisaCuidadosEspeciais(),
                gato.getDescricaoCuidados(),
                gato.getDescricaoAparencia(),
                gato.getVacinado(),
                gato.getId()
        );

        if (linhasAfetadas == 0) {
            return false;
        }

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getId().equals(gato.getId())) {
                listaGatos.set(i, gato);
                break;
            }
        }

        return true;
    }


    public boolean excluirGato(Integer id) {

        if (id == null || id <= 0) {
            return false;
        }

        Gato gatoEncontrado = null;

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getId().equals(id)) {
                gatoEncontrado = listaGatos.get(i);
                break;
            }
        }

        if (gatoEncontrado == null) {
            return false;
        }

        jdbcTemplate.update(
                "DELETE FROM gatoFilhote WHERE fk_gato = ?", id
        );

        jdbcTemplate.update(
                "DELETE FROM gatoAdulto WHERE fk_gato = ?", id
        );

        int linhasAfetadas = jdbcTemplate.update(
                "DELETE FROM gato WHERE id = ?", id
        );

        if (linhasAfetadas > 0) {
            listaGatos.remove(gatoEncontrado);
            return true;
        }

        return false;
    }

    public void adotarGatinho(Gato gato) {
        String sql = """
        UPDATE gato
        SET adotado = ?
        WHERE id = ?
        """;

        jdbcTemplate.update(sql, true, gato.getId());

        for (int i = 0; i < listaGatos.size(); i++) {
            if (listaGatos.get(i).getId().equals(gato.getId())) {
                listaGatos.get(i).setAdotado(true);
                break;
            }
        }
    }

}
