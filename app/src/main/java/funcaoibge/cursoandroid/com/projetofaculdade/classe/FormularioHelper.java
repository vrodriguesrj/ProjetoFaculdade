package funcaoibge.cursoandroid.com.projetofaculdade.classe;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

import funcaoibge.cursoandroid.com.projetofaculdade.FormularioActivity;
import funcaoibge.cursoandroid.com.projetofaculdade.MainActivity;
import funcaoibge.cursoandroid.com.projetofaculdade.R;

/**
 * Created by marcos.j.estagiario on 06/03/2017.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoSobrenome;
    private final EditText campoEmail;
    private final EditText campoTelefone;

    private Contato contato;
    private Random random = new Random();
    private Context context;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoSobrenome = (EditText) activity.findViewById(R.id.formulario_sobrenome);
        campoEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        context = activity.getBaseContext();
    }

    public Contato obterContato(long id) {

        int[] intArray = context.getResources().getIntArray(R.array.androidcolors);
        int corAleatoria = intArray[random.nextInt(intArray.length)];

        if (campoNome.getText().toString().equals("") || (campoTelefone.getText().toString().equals("")))
            return null;

        contato = new Contato();
        contato.setId(id);
        contato.setNome((campoNome.getText().toString()));
        contato.setSobrenome(campoSobrenome.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
        contato.setTelefone(campoTelefone.getText().toString());
        contato.setCor(corAleatoria);
        return contato;
    }

    public void preencheContato(Contato contato) {
        campoNome.setText(contato.getNome());
        campoSobrenome.setText(contato.getSobrenome());
        campoEmail.setText(contato.getEmail());
        campoTelefone.setText(contato.getTelefone());
        this.contato = contato;
    }
}
