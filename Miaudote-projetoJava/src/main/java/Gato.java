public class Gato {

    private Integer id;
    private String nome;
    private String sexo;
    private Integer idadeMeses;
    private Boolean adotado;
    private String personalidade;
    private Boolean precisaCuidadosEspeciais;
    private String descricaoCuidados;
    private String descricaoAparencia;
    private Boolean vacinado;

    public Gato (){

    }
    public Gato( String nome, String sexo, Integer idadeMeses, String personalidade, Boolean precisaCuidadosEspeciais, String descricaoCuidados, String descricaoAparencia, Boolean vacinado) {
        if ( nome == null || nome.isBlank() || sexo == null || sexo.isBlank() || idadeMeses == null
                || idadeMeses <= 0 || personalidade == null || personalidade.isBlank() || precisaCuidadosEspeciais == null
                || descricaoAparencia == null || descricaoAparencia.isBlank() || vacinado == null){
            throw new DadoInvalidoException("dados inválidos");
        }

        if (precisaCuidadosEspeciais &&
                (descricaoCuidados == null || descricaoCuidados.isBlank())) {
            throw new DadoInvalidoException(
                    "Se o gatinho precisa de cuidados especiais, informe a descrição"
            );
        }

        this.nome = nome;
        this.sexo = sexo;
        this.idadeMeses = idadeMeses;
        this.personalidade = personalidade;
        this.adotado = false;
        this.precisaCuidadosEspeciais = precisaCuidadosEspeciais;
        this.descricaoCuidados = descricaoCuidados;
        this.descricaoAparencia = descricaoAparencia;
        this.vacinado = vacinado;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null || id <= 0) {
            throw new DadoInvalidoException("ID inválido");
        }

        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()){
            throw new DadoInvalidoException("nome inválido");
        }
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        if (sexo == null || sexo.isBlank()){
            throw new DadoInvalidoException("sexo inválido");
        }
        this.sexo = sexo;
    }

    public Integer getIdadeMeses() {
        return idadeMeses;
    }

    public void setIdadeMeses(Integer idadeMeses) {
        if (idadeMeses == null || idadeMeses <= 0) {
            throw new DadoInvalidoException("Idade inválida");
        }

        this.idadeMeses = idadeMeses;
    }

    public Boolean getAdotado() {
        return adotado;
    }

    public void setAdotado(Boolean adotado) {
        if (adotado == null){
            throw new DadoInvalidoException("atributo inválido");
        }
        this.adotado = adotado;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        if (personalidade == null || personalidade.isBlank()){
            throw new DadoInvalidoException("personalidade inválido");
        }
        this.personalidade = personalidade;
    }

    public Boolean getPrecisaCuidadosEspeciais() {
        return precisaCuidadosEspeciais;
    }

    public void setPrecisaCuidadosEspeciais(Boolean precisaCuidadosEspeciais) {
        if (precisaCuidadosEspeciais == null){
            throw new DadoInvalidoException("atributo inválido");
        }
        this.precisaCuidadosEspeciais = precisaCuidadosEspeciais;
    }

    public String getDescricaoCuidados() {
        return descricaoCuidados;
    }

    public void setDescricaoCuidados(String descricaoCuidados) {
        if (precisaCuidadosEspeciais &&
                (descricaoCuidados == null || descricaoCuidados.isBlank())) {
            throw new DadoInvalidoException(
                    "Se o gatinho precisa de cuidados especiais, informe a descrição"
            );
        }
        this.descricaoCuidados = descricaoCuidados;
    }

    public String getDescricaoAparencia() {
        return descricaoAparencia;
    }

    public void setDescricaoAparencia(String descricaoAparencia) {
        if (descricaoAparencia == null || descricaoAparencia.isBlank()){
            throw new DadoInvalidoException("descrição da aparência inválida");
        }
        this.descricaoAparencia = descricaoAparencia;
    }

    public Boolean getVacinado() {
        return vacinado;
    }

    public void setVacinado(Boolean vacinado) {
        if (vacinado == null){
            throw new DadoInvalidoException("atributo vacinado inválido");
        }
        this.vacinado = vacinado;
    }

    @Override
    public String toString() {
        return "Gato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", idadeMeses=" + idadeMeses +
                ", adotado=" + adotado +
                ", personalidade='" + personalidade + '\'' +
                ", precisaCuidadosEspeciais=" + precisaCuidadosEspeciais +
                ", descricaoCuidados='" + descricaoCuidados + '\'' +
                ", descricaoAparencia='" + descricaoAparencia + '\'' +
                ", vacinado=" + vacinado +
                '}';
    }
}

