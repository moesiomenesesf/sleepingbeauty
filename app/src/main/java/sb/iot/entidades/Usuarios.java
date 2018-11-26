package sb.iot.entidades;

public class Usuarios {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String sexo;
    private Double batimentos;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getBatimentos() {
        return batimentos;
    }

    public void setBatimentos(Double batimentos) {
        this.batimentos = batimentos;
    }
}
