package funcaoibge.cursoandroid.com.projetofaculdade.eventos;

import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;

/**
 * Created by marcos.j.estagiario on 07/03/2017.
 */

public class RecebeContatoAdapter {

    Contato contato;

    public RecebeContatoAdapter(Contato contato) {
        this.contato = contato;
    }

    public Contato getContato() {
        return contato;
    }
}
