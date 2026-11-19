public class GatoAdulto extends Gato{

    private Boolean castrado;
    private String historicoComportamental;

    public GatoAdulto(String nome, String sexo, Integer idadeMeses, String personalidade, Boolean precisaCuidadosEspeciais, String descricaoCuidados, String descricaoAparencia, Boolean vacinado, Boolean castrado, String historicoComportamental) {
        super(nome, sexo, idadeMeses, personalidade, precisaCuidadosEspeciais, descricaoCuidados, descricaoAparencia, vacinado);
        if (castrado == null || historicoComportamental == null || historicoComportamental.isBlank()){
            throw new DadoInvalidoException("Dados inválidos");
        }
        this.castrado = castrado;
        this.historicoComportamental = historicoComportamental;
    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        if (castrado == null){
            throw new DadoInvalidoException("atributo castrado inválido");
        }
        this.castrado = castrado;
    }

    public String getHistoricoComportamental() {
        return historicoComportamental;
    }

    public void setHistoricoComportamental(String historicoComportamental) {
        if (historicoComportamental == null || historicoComportamental.isBlank()){
            throw new DadoInvalidoException("historico comportamental inválido");
        }
        this.historicoComportamental = historicoComportamental;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", GatoAdulto{" +
                "castrado=" + castrado +
                ", historicoComportamental='" + historicoComportamental + '\'' +
                '}';
    }
}
