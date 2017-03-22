package funcaoibge.cursoandroid.com.projetofaculdade.classe;

import android.widget.EditText;

import funcaoibge.cursoandroid.com.projetofaculdade.FormularioActivity;
import funcaoibge.cursoandroid.com.projetofaculdade.R;

/**
 * Created by marcos.j.estagiario on 06/03/2017.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private Contato contato;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
    }

    public Contato obterContato(long id) {
        if (campoNome.getText().toString().equals("") || (campoTelefone.getText().toString().equals("")))
            return null;

        contato = new Contato();
        contato.setId(id);
        contato.setNome((campoNome.getText().toString()));
        contato.setEmail(campoEmail.getText().toString());
        contato.setTelefone(campoTelefone.getText().toString());
        return contato;
    }

    public void preencheContato(Contato contato) {
        campoNome.setText(contato.getNome());
        campoEmail.setText(contato.getEmail());
        campoTelefone.setText(contato.getTelefone());
        this.contato = contato;
    }
}
