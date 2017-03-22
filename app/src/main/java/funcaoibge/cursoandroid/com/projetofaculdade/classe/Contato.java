package funcaoibge.cursoandroid.com.projetofaculdade.classe;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marcos.j.estagiario on 02/03/2017.
 */

public class Contato implements Serializable {
    private long id;
    private String nome;
    private String email;
    private String telefone;

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
