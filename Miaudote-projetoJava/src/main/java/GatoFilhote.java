public class GatoFilhote extends Gato{
    private Boolean desmamado;

    public GatoFilhote(String nome, String sexo, Integer idadeMeses, String personalidade, Boolean precisaCuidadosEspeciais, String descricaoCuidados, String descricaoAparencia, Boolean vacinado, Boolean desmamado) {
        super(nome, sexo, idadeMeses, personalidade, precisaCuidadosEspeciais, descricaoCuidados, descricaoAparencia, vacinado);
        if (desmamado == null){
            throw new DadoInvalidoException("atributo desmamado inválido");
        }

        this.desmamado = desmamado;
    }

    public Boolean getDesmamado() {
        return desmamado;
    }

    public void setDesmamado(Boolean desmamado) {
        if (desmamado == null){
            throw new DadoInvalidoException("atributo desmamado inválido");
        }
        this.desmamado = desmamado;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", GatoFilhote{" +
                "desmamado=" + desmamado +
                '}';
    }


}
