package funcaoibge.cursoandroid.com.projetofaculdade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;
import funcaoibge.cursoandroid.com.projetofaculdade.classe.FormularioHelper;
import funcaoibge.cursoandroid.com.projetofaculdade.dao.ContatoDAO;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private long idContato;
    private TextInputLayout erroNome;
    private TextInputLayout erroTelefone;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato_preenchimento);

        mToolbar = (Toolbar) findViewById(R.id.projeto_toolbar);
        erroNome = (TextInputLayout) findViewById(R.id.erro_nome);
        erroTelefone = (TextInputLayout) findViewById(R.id.erro_telefone);

        configurarSupportActionBar();
        obterContato();
    }

    private void configurarSupportActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void obterContato() {
        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");
        if (contato != null) {
            helper.preencheContato(contato);
            idContato = contato.getId();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.confirmar_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salvar_contato) {
            Contato contato = helper.obterContato(idContato);
            ContatoDAO dao = new ContatoDAO(this);

            if (contato != null) {
                if (contato.getId() == 0) {
                    dao.insere(contato);
                    Toast.makeText(FormularioActivity.this, "Contato " + contato.getNome() + " salvo", Toast.LENGTH_SHORT).show();
                } else
                    dao.altera(contato);
                Toast.makeText(FormularioActivity.this, "Contato " + contato.getNome() + " alterado", Toast.LENGTH_SHORT).show();
                dao.close();
                finish();
            } else {
                erroNome.setError("Campo nome obrigatório...");
                erroTelefone.setError("Campo telefone obrigatório...");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
